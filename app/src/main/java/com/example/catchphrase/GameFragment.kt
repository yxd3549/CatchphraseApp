package com.example.catchphrase

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * The GameFragment implements all the game logic
 */
class GameFragment : Fragment() {

    companion object {
        const val GAME_CATEGORY = "game_category"
        const val TEAM1 = "team1"
        const val TEAM2 = "team2"
        const val TIME_PER_ROUND: Long = 40000
        const val ONE_SECOND: Long = 1000
        const val SECONDS_IN_MINUTE: Long = 60
    }


    var words = mutableListOf<String>()
    var currIndex = 0
    var category = ""
    var team1 = ""
    var team2 = ""
    var team1Score = 0
    var team2Score = 0
    var counter: CountDownTimer? = null
    var listener: GameFragmentListener? = null
    var paused = false
    var tickingPlayer: MediaPlayer? = null
    var explosionPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? GameFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement GameFragmentListener")
        }
    }

    /**
     * Initialize all TextViews, fetch words, and set click listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.category = arguments!!.getString(GAME_CATEGORY)!!
        this.team1 = arguments!!.getString(TEAM1)!!
        this.team2 = arguments!!.getString(TEAM2)!!
        this.team1Score = 0
        this.team2Score = 0

        fetchWords()

        team1_name.text = team1
        team2_name.text = team2
        team1_score.text = team1Score.toString()
        team2_score.text = team2Score.toString()

        next_button.setOnClickListener { next() }
        stop_button.setOnClickListener { stopButtonHandler() }
        team1_button.setOnClickListener { team1ButtonHandler() }
        team2_button.setOnClickListener { team2ButtonHandler() }
    }

    /**
     * Starts the ticking sound and sets up the explosion sound
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Catchphrase"
        startCounter()
        tickingPlayer = MediaPlayer.create(context, R.raw.ticking)
        explosionPlayer = MediaPlayer.create(context, R.raw.explosion)
        tickingPlayer?.start()
    }

    /**
     * Initializes and starts the counter
     */
    private fun startCounter(){
        counter = object:CountDownTimer(TIME_PER_ROUND, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes: Long = millisUntilFinished / ONE_SECOND / SECONDS_IN_MINUTE
                val seconds = (millisUntilFinished / ONE_SECOND % SECONDS_IN_MINUTE)
                timer?.text = "$minutes:$seconds"
            }
            override fun onFinish() {
                paused = true
                timer?.text = "TIME!"
                tickingPlayer?.pause()
                explosionPlayer?.seekTo(0)
                explosionPlayer?.start()
                team1_button.isEnabled = true
                team2_button.isEnabled = true
            }
        }
        (counter as CountDownTimer).start()
    }

    /**
     * Retrieves the words to play with from the database
     */
    private fun fetchWords(){
        doAsync {
            words = PhraseDatabase.getInstance(activity!!).phraseDao()
                .getPhrasesInCategory(category).toMutableList()
            uiThread {
                words.shuffle()
                word.text = words[currIndex]
            }
        }
    }

    private fun stopButtonHandler(){
        counter?.cancel()
        timer?.text = "STOPPED"
        paused = true
        team1_button.isEnabled = true
        team2_button.isEnabled = true
        tickingPlayer?.pause()
    }

    private fun team1ButtonHandler(){
        team1Score++
        if(team1Score == 7){
            listener?.endGame(team1, team1Score, team2Score)
        }
        team1_score.text = team1Score.toString()
    }

    private fun team2ButtonHandler(){
        team2Score++
        if(team2Score == 7){
            listener?.endGame(team2, team1Score, team2Score)
        }
        team2_score.text = team2Score.toString()
    }

    override fun onPause() {
        super.onPause()
        tickingPlayer?.stop()
        explosionPlayer?.stop()
    }

    private fun next(){
        if (paused){
            counter?.start()
            explosionPlayer?.pause()
            tickingPlayer?.seekTo(0)
            tickingPlayer?.start()
            paused = false
            team1_button.isEnabled = false
            team2_button.isEnabled = false

            currIndex++
            word.text = words[currIndex%words.size]
        } else {
            currIndex++
            word.text = words[currIndex%words.size]
        }
    }


    interface GameFragmentListener {
        fun endGame(winningTeam: String, team1Score: Int, team2Score: Int)
    }


}

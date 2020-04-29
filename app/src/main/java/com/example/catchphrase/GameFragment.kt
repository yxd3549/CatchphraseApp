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
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    companion object {
        const val GAME_CATEGORY = "game_category"
        const val TEAM1 = "team1"
        const val TEAM2 = "team2"
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? GameFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement GameFragmentListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.category = arguments!!.getString(GAME_CATEGORY)!!
        this.team1 = arguments!!.getString(TEAM1)!!
        this.team2 = arguments!!.getString(TEAM2)!!
        this.team1Score = 0
        this.team2Score = 0
        doAsync {
            words = PhraseDatabase.getInstance(activity!!).phraseDao()
                .getPhrasesInCategory(category).toMutableList()
            uiThread {
                words.shuffle()
                word.text = words[currIndex]
            }
        }

        team1_name.text = team1
        team2_name.text = team2
        team1_score.text = team1Score.toString()
        team2_score.text = team2Score.toString()

        next_button.setOnClickListener {
            next()
        }

        stop_button.setOnClickListener {
            counter?.cancel()
            timer?.text = "STOPPED"
            paused = true
            team1_button.isEnabled = true
            team2_button.isEnabled = true
        }

        team1_button.setOnClickListener {
            team1Score++
            if(team1Score == 7){
                listener?.endGame(team1, team1Score, team2Score)
            }
            team1_score.text = team1Score.toString()
        }

        team2_button.setOnClickListener {
            team2Score++
            if(team2Score == 7){
                listener?.endGame(team2, team1Score, team2Score)
            }
            team2_score.text = team2Score.toString()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Catchphrase"
        tickingPlayer = MediaPlayer.create(context, R.raw.ticking)
        explosionPlayer = MediaPlayer.create(context, R.raw.explosion)
        counter = object:CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes: Long = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000 % 60)
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
        tickingPlayer?.start()
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

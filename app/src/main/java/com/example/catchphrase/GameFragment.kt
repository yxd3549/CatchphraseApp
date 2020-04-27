package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    companion object {
        const val GAME_CATEGORY = "game_category"
        const val TEAM1 = "team1"
        const val TEAM2 = "team2"
    }

    var words = arrayOf("Steven", "Garnet", "Ruby", "Sapphire", "Amethyst",
        "Pearl", "Greg", "Connie", "Lars", "Sadie", "Rose", "Lapis", "Peridot",
        "Jasper", "Bismuth", "Lion", "Spinel", "Opal", "Sugilite", "Alexandrite",
        "Stevonnie", "Malachite", "Rainbow Quarts", "Sardonyx", "Smoky Quartz",
        "Sunstone", "Obsidian", "Steg").toMutableList()
    var currIndex = 0
    var category = ""
    var team1 = ""
    var team2 = ""
    var team1Score = 0
    var team2Score = 0
    var counter: CountDownTimer? = null
    var listener: GameFragmentListener? = null
    var paused = false

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
        this.words.shuffle()

        word.text = words[currIndex]

        team1_name.text = team1
        team2_name.text = team2
        team1_score.text = team1Score.toString()
        team2_score.text = team2Score.toString()

        next_button.setOnClickListener {
            next()
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
        counter = object:CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes: Long = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000 % 60)
                timer?.text = "$minutes:$seconds"
            }
            override fun onFinish() {
                paused = true
                timer?.text = "TIME!"
            }
        }
        (counter as CountDownTimer).start()
    }

    private fun next(){
        if (paused){
            counter?.start()
            paused = false
        } else {
            currIndex++
            word.text = words[currIndex%words.size]
        }
    }

    interface GameFragmentListener {
        fun endGame(winningTeam: String, team1Score: Int, team2Score: Int)
    }


}

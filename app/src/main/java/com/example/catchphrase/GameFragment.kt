package com.example.catchphrase

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

    var category = ""
    var team1 = ""
    var team2 = ""
    var currentTeam = 1
    var team1Score = 0
    var team2Score = 0
    var counter: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.category = arguments!!.getString(GAME_CATEGORY)!!
        this.team1 = arguments!!.getString(TEAM1)!!
        this.team2 = arguments!!.getString(TEAM2)!!
        this.team1Score = 0
        this.team2Score = 0

        turn_label.text = "$team1's turn"
        team1_name.text = team1
        team2_name.text = team2
        team1_score.text = team1Score.toString()
        team2_score.text = team2Score.toString()

        next_button.setOnClickListener { counter?.start() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Catchphrase"
        counter = object:CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes: Long = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000 % 60)
                timer.text = "$minutes:$seconds"
            }
            override fun onFinish() {
                if(currentTeam == 1) {
                    team2Score++
                    team2_score.text = team2Score.toString()
                    currentTeam = 2
                } else {
                    team1Score++
                    team1_score.text = team1Score.toString()
                    currentTeam = 1
                }
                timer.text = "TIME!"
            }
        }
        (counter as CountDownTimer).start()
    }


}

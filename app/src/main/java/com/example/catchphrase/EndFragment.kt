package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import android.sax.EndElementListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_end.*

/**
 * A simple [Fragment] subclass.
 */
class EndFragment : Fragment() {

    companion object{
        const val WINNING_TEAM = "winning_team"
        const val TEAM1 = "team1"
        const val TEAM2 = "team2"
        const val TEAM1_SCORE = "team1_score"
        const val TEAM2_SCORE = "team2_score"
    }

    var listener: EndFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EndFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement EndFragmentListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        team1_name.text = arguments!!.getString(TEAM1)!!
        team2_name.text = arguments!!.getString(TEAM2)!!
        val team1Score =  arguments!!.getInt(TEAM1_SCORE).toString()
        team1_score.text = "Score: $team1Score"
        val team2Score =  arguments!!.getInt(TEAM2_SCORE).toString()
        team2_score.text = "Score: $team2Score"
        val winningTeam = arguments!!.getString(WINNING_TEAM)
        winning_message.text = "$winningTeam Wins!"
        play_again_button.setOnClickListener { listener?.playAgain() }
    }

    interface EndFragmentListener {
        fun playAgain()
    }

}

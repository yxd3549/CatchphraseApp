package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_teams.*

/**
 * A simple [Fragment] subclass.
 */
class TeamsFragment : Fragment() {

    var listener: TeamsFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? TeamsFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        select_teams_button.setOnClickListener {
            listener?.selectTeamsClicked(team1_name.text.toString(), team2_name.text.toString())
        }
    }

    interface TeamsFragmentListener {
        fun selectTeamsClicked(team1: String, team2: String)
    }

}

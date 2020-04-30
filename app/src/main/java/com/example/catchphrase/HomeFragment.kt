package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * The HomeFragment displays the home screen with options for the user
 */
class HomeFragment : Fragment() {

    var listener: HomeFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? HomeFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement HomeFragmentListener")
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Catchphrase"
        selectCategoryButton.setOnClickListener { listener?.onSelectCategoryClicked() }
        newCategoryButton.setOnClickListener { listener?.onNewCategoryClicked() }
        howToPlayButton.setOnClickListener { listener?.onHowToPlayClicked() }
    }

    interface HomeFragmentListener {
        fun onSelectCategoryClicked()
        fun onNewCategoryClicked()
        fun onHowToPlayClicked()
    }

}

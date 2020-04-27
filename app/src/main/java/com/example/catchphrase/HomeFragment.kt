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
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var listener: HomeFragmentListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        selectCategoryButton.setOnClickListener { onSelectCategory() }
        newCategoryButton.setOnClickListener { onNewCategory() }
        howToPlayButton.setOnClickListener { onHowToPlay() }
    }

    fun onSelectCategory(){
        listener?.onSelectCategoryClicked()
    }

    fun onNewCategory(){
        listener?.onNewCategoryClicked()
    }

    fun onHowToPlay(){
        listener?.onHowToPlayClicked()
    }
    interface HomeFragmentListener {
        fun onSelectCategoryClicked()
        fun onNewCategoryClicked()
        fun onHowToPlayClicked()
    }

}

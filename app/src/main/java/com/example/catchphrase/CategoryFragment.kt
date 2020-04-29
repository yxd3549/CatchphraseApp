package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    var listener: CategoryFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? CategoryFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement CategoryFragmentListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Select Category"
        val spinner: Spinner = category_input
        var categories: List<String>
        doAsync{
            categories = PhraseDatabase.getInstance(activity!!).phraseDao().getAllCategories()
            uiThread {
                val spinnerArrayAdapter: ArrayAdapter<String> =
                    ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, categories)
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = spinnerArrayAdapter
                select_category_button.setOnClickListener {
                    listener?.onNextClicked(spinner.selectedItem.toString())
                }
            }
        }
    }

    interface CategoryFragmentListener {
        fun onNextClicked(categoryName: String)
    }

}

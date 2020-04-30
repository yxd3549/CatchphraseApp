package com.example.catchphrase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_category.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * The CategoryFragment fetches and displays all the categories for the user to choose
 */
class CategoryFragment : Fragment() {

    var listener: CategoryFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        fetchCategories()
    }

    /**
     * Fetch a list of categories from the database.
     * The default category is Android themed!
     */
    private fun fetchCategories() {
        var categories: List<String>
        doAsync{
            categories = PhraseDatabase.getInstance(activity!!).phraseDao().getAllCategories()
            uiThread {
                val spinnerArrayAdapter: ArrayAdapter<String> =
                    ArrayAdapter<String>(activity!!, R.layout.dropdown_menu_popup_item, categories)
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                filled_exposed_dropdown.setAdapter(spinnerArrayAdapter)
                filled_exposed_dropdown.setText("Android", false)
                select_category_button.setOnClickListener {
                    listener?.onNextClicked(filled_exposed_dropdown.text.toString())
                }
            }
        }
    }

    interface CategoryFragmentListener {
        fun onNextClicked(categoryName: String)
    }

}

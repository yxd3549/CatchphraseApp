package com.example.catchphrase

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.catchphrase.entities.Phrase
import kotlinx.android.synthetic.main.fragment_new_category.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

/**
 * The NewCategoryFragment allows the user to make their own categories
 */
class NewCategoryFragment : Fragment() {

    var listener : NewCategoryFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_category, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewCategoryFragmentListener
        if (listener == null) {
            throw ClassCastException("$context must implement NewCategoryFragmentListener")
        }
    }

    /**
     * Sets up the create button listener and a listener for the number of words entered
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "New Category"

        create_button.setOnClickListener { createButtonHandler()}

        phrases_input_text.doOnTextChanged { text, start, count, after ->
            val words = phrases_input_text.text.toString().split(", ")
            word_count.text = "Word count: " + words.size.toString()
        }
    }

    /**
     * Handles when the user clicks on create.
     * It does some input validation to make sure the user doesn't accidentally do something silly
     */
    private fun createButtonHandler(){
        hideKeyboard()
        val category = category_name_edit_text.text.toString()
        val words = phrases_input_text.text.toString().split(", ")
        if (words.size < 2){
            Toast.makeText(activity!!,
                "Please add more words. Let's make it fun!", Toast.LENGTH_LONG).show()
        } else if(category ==""){
            Toast.makeText(activity!!,
                "Make sure you name your category. Make it unique, like you <3", Toast.LENGTH_LONG).show()
        } else {
            addCategory(category, words)
        }
    }

    /**
     * Add a new category to the database
     */
    private fun addCategory(category: String, phrases: List<String> ) {
        var phraseEntities: MutableList<Phrase> = arrayListOf()
        phrases.forEach {
            val phrase = Phrase(it, category)
            phraseEntities.add(phrase)
        }
        doAsync {
            phraseEntities.forEach{
                PhraseDatabase.getInstance(activity!!).phraseDao().insertPhrase(it)
            }
            uiThread { Toast.makeText(activity!!,
                "Your new category has been created. Go play!", Toast.LENGTH_LONG).show()
                listener?.categoryCreated()}
        }
    }

    /**
     * Hides the keyboard so the user can see the toasts
     */
    private fun hideKeyboard(){
        val inputManager: InputMethodManager =
            context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity!!.currentFocus?.windowToken, 0)
    }

    interface NewCategoryFragmentListener {
        fun categoryCreated();
    }

}

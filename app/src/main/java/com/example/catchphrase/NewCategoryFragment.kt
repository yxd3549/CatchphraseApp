package com.example.catchphrase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * A simple [Fragment] subclass.
 */
class NewCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "New Category"

        create_button.setOnClickListener {
            val category = categoryNameInput.text.toString()
            val words = phrasesInput.text.toString().split(", ")
            addCategory(category, words)
        }
        phrasesInput.doOnTextChanged { text, start, count, after ->
            val words = phrasesInput.text.toString().split(", ")
            word_count.text = "Word count: " + words.size.toString()
        }
    }

    fun addCategory(category: String, phrases: List<String> ) {
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
                "Your new category has been created. Go play!", Toast.LENGTH_LONG).show()  }
        }
    }

}

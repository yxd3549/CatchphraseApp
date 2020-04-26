package com.example.catchphrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catchphrase.dummy.DummyContent

class MainActivity : AppCompatActivity(),
    HomeFragment.HomeFragmentListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Are we displaying a fragment in the top/left
        if(supportFragmentManager.findFragmentById(R.id.content) == null){
            // No
            supportFragmentManager.beginTransaction()
                .add(R.id.content, HomeFragment(), "home")
                .commit()
        }
    }

    override fun onSelectCategoryClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("home")
            .remove(supportFragmentManager.findFragmentByTag("home")!!)
            .add(R.id.content, CategoryFragment(), "categories")
            .commit()
    }

    override fun onNewCategoryClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("home")
            .remove(supportFragmentManager.findFragmentByTag("home")!!)
            .add(R.id.content, NewCategoryFragment(), "new")
            .commit()
    }

    override fun onHowToPlayClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("home")
            .remove(supportFragmentManager.findFragmentByTag("home")!!)
            .add(R.id.content, HowToPlayFragment(), "howto")
            .commit()
    }

}

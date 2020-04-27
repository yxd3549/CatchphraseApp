package com.example.catchphrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catchphrase.dummy.DummyContent

class MainActivity : AppCompatActivity(),
    HomeFragment.HomeFragmentListener,
    CategoryFragment.CategoryFragmentListener,
    TeamsFragment.TeamsFragmentListener{

    var category = ""
    var team1 = ""
    var team2 = ""

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
        supportActionBar?.title = "Select Category"
    }

    override fun onNewCategoryClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("home")
            .remove(supportFragmentManager.findFragmentByTag("home")!!)
            .add(R.id.content, NewCategoryFragment(), "new")
            .commit()
        supportActionBar?.title = "New Category"
    }

    override fun onHowToPlayClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("home")
            .remove(supportFragmentManager.findFragmentByTag("home")!!)
            .add(R.id.content, HowToPlayFragment(), "howto")
            .commit()
        supportActionBar?.title = "How to Play"
    }

    override fun onNextClicked(selectedCategory: String) {
        category = selectedCategory
        supportFragmentManager.beginTransaction()
            .addToBackStack("categories")
            .remove(supportFragmentManager.findFragmentByTag("categories")!!)
            .add(R.id.content, TeamsFragment(), "teams")
            .commit()
        supportActionBar?.title = "Choose Team Names"
    }

    override fun selectTeamsClicked(team1: String, team2: String) {
        this.team1 = team1
        this.team2 = team2
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("teams")!!)
            .add(R.id.content, GameFragment(), "game")
            .commit()
        supportActionBar?.title = "Catchphrase - $category"
    }

}

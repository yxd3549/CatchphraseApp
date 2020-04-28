package com.example.catchphrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity(),
    HomeFragment.HomeFragmentListener,
    CategoryFragment.CategoryFragmentListener,
    TeamsFragment.TeamsFragmentListener,
    GameFragment.GameFragmentListener,
    EndFragment.EndFragmentListener{

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

    override fun onNextClicked(selectedCategory: String) {
        category = selectedCategory
        supportFragmentManager.beginTransaction()
            .addToBackStack("categories")
            .remove(supportFragmentManager.findFragmentByTag("categories")!!)
            .add(R.id.content, TeamsFragment(), "teams")
            .commit()
    }

    override fun selectTeamsClicked(team1: String, team2: String) {
        this.team1 = team1
        this.team2 = team2
        val gameFragment = GameFragment()
        val args = Bundle()
        args.putString(GameFragment.GAME_CATEGORY, this.category)
        args.putString(GameFragment.TEAM1, this.team1)
        args.putString(GameFragment.TEAM2, this.team2)
        gameFragment.arguments = args
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("teams")!!)
            .add(R.id.content, gameFragment, "game")
            .commit()
    }

    override fun endGame(winningTeam: String, team1Score: Int, team2Score: Int) {
        val endFragment = EndFragment()
        val args = Bundle()
        args.putString(EndFragment.WINNING_TEAM, winningTeam)
        args.putString(EndFragment.TEAM1, this.team1)
        args.putString(EndFragment.TEAM2, this.team2)
        args.putInt(EndFragment.TEAM1_SCORE, team1Score)
        args.putInt(EndFragment.TEAM2_SCORE, team2Score)
        endFragment.arguments = args
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("game")!!)
            .add(R.id.content, endFragment, "end")
            .commit()

    }

    override fun playAgain() {
        supportFragmentManager.beginTransaction()
            .remove(supportFragmentManager.findFragmentByTag("end")!!)
            .add(R.id.content, HomeFragment(), "home")
            .commit()
    }

}

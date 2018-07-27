package id.co.imastudio.kadeproject.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import fragment.FavoriteFragment
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.fragment.MatchFragment
import id.co.imastudio.kadeproject.fragment.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_for_fragment, MatchFragment(), MatchFragment::class.simpleName)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_for_fragment, TeamFragment(), TeamFragment::class.simpleName)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_for_fragment, FavoriteFragment(), FavoriteFragment::class.simpleName)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_for_fragment, MatchFragment(), MatchFragment::class.simpleName)
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}

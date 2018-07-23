package id.co.imastudio.kadeproject.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.fragment.FavoriteFragment
import id.co.imastudio.kadeproject.fragment.NextMatchFragment
import id.co.imastudio.kadeproject.fragment.PreviousMatchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_for_fragment, PreviousMatchFragment(), PreviousMatchFragment::class.simpleName)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_for_fragment, NextMatchFragment(), NextMatchFragment::class.simpleName)
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
                .replace(R.id.frame_for_fragment, PreviousMatchFragment(), PreviousMatchFragment::class.simpleName)
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

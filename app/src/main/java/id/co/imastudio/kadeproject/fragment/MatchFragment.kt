package id.co.imastudio.kadeproject.fragment


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.*
import android.widget.SearchView
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.R.layout.fragment_match
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.ctx



class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(fragment_match, container, false)
    }
    private lateinit var pagerAdapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = PagerAdapter(fragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Switch to view for this tab
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return NextMatchFragment()
                1 -> return PreviousMatchFragment()

                else -> return null
            }
        }

        override fun getCount(): Int {
            return 2;
        }

        private val tabTitles = arrayOf("Next", "Past")

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }

    }



}



package id.co.imastudio.kadeproject.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.database.FavoriteTeam
import id.co.imastudio.kadeproject.database.database
import id.co.imastudio.kadeproject.favorite.FavoriteTeamAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FavoriteTeamFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FavoriteTeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoriteTeamFragment : Fragment(), AnkoComponent<Context> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

//            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.listTeam
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                }
            }
        }

    }



    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()

    private lateinit var adapter: FavoriteTeamAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapter = FavoriteTeamAdapter(favorites);
        listTeam.adapter = adapter

        showFavorite()

        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_TEAM)
            val team = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(team)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        favorites.clear()
        showFavorite()
    }
}

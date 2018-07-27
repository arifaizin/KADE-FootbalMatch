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
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.model.PlayersItem
import id.co.imastudio.kadeproject.player.PlayerAdapter
import id.co.imastudio.kadeproject.player.PlayerPresenter
import id.co.imastudio.kadeproject.player.PlayerView
import id.co.imastudio.kadeproject.utils.invisible
import id.co.imastudio.kadeproject.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), AnkoComponent<Context>, PlayerView {

    private lateinit var listPlayer: RecyclerView
    private lateinit var adapter: PlayerAdapter


    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var players: MutableList<PlayersItem> = mutableListOf(
    )

    private lateinit var presenter: PlayerPresenter


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

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }

    }


    private var parameter: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        parameter = getArguments()?.getString("parameter")

        //set adapter
        adapter = PlayerAdapter(players)
        listPlayer.adapter = adapter

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(parameter)


        swipeRefresh.onRefresh {
            presenter.getPlayerList(parameter)
        }
    }


    override fun showPlayerList(data: List<PlayersItem>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


}


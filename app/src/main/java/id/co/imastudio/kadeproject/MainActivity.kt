package id.co.imastudio.kadeproject

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.google.gson.Gson
import id.co.imastudio.kadeproject.R.color.colorAccent
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.model.EventsItem
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView {


//    override fun showTeamList(data: List<Team>) {
//        swipeRefresh.isRefreshing = false
//        teams.clear()
//        teams.addAll(data)
//        adapter.notifyDataSetChanged()
//    }

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

//    private var teams: MutableList<Team> = mutableListOf()
    private var events: MutableList<EventsItem> = mutableListOf()

    private lateinit var presenter: MainPresenter
//    private lateinit var adapter: MainAdapter
    private lateinit var adapterMatch: MatchAdapter

    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    private var posisi: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
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

//        adapter = MainAdapter(teams)
//        listTeam.adapter = adapter

        //set adapter
        adapterMatch = MatchAdapter(events);
        listTeam.adapter = adapterMatch

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = MainPresenter(this, request, gson)

        //spinner
//        val spinnerItems = resources.getStringArray(league)
        val spinnerItems = resources.getStringArray(R.array.event_type)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                leagueName = spinner.selectedItem.toString()
                posisi = position
                presenter.getNextEvent(posisi)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getNextEvent(posisi)
        }
    }


    //imp mainView
    override fun showMatchList(data: List<EventsItem>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapterMatch.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}

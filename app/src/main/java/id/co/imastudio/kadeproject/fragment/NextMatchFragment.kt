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
import id.co.imastudio.kadeproject.*
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.model.EventsItem
import org.jetbrains.anko.*
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
 *
 */
class NextMatchFragment : Fragment(), AnkoComponent<Context>, MainView {

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

                    listEvent = recyclerView {
                        id = R.id.listEvent
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


//    override fun showTeamList(data: List<Team>) {
//        swipeRefresh.isRefreshing = false
//        teams.clear()
//        teams.addAll(data)
//        adapter.notifyDataSetChanged()
//    }

    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    //    private var teams: MutableList<Team> = mutableListOf()
    private var events: MutableList<EventsItem> = mutableListOf()

    private lateinit var presenter: MainPresenter
    //    private lateinit var adapter: MainAdapter
    private lateinit var adapterMatch: MatchAdapter

    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    private var posisi: Int = 1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapterMatch = MatchAdapter(events);
        listEvent.adapter = adapterMatch

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = MainPresenter(this, request, gson)
        presenter.getNextEvent(posisi)

//        //spinner
////        val spinnerItems = resources.getStringArray(league)
//        val spinnerItems = resources.getStringArray(R.array.event_type)
//        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
//        spinner.adapter = spinnerAdapter
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
////                leagueName = spinner.selectedItem.toString()
//                posisi = position
//                presenter.getNextEvent(posisi)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }

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

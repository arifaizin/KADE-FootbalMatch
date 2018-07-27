package id.co.imastudio.kadeproject.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.home.MatchAdapter
import id.co.imastudio.kadeproject.home.MatchPresenter
import id.co.imastudio.kadeproject.home.MatchView
import id.co.imastudio.kadeproject.model.EventsItem
import id.co.imastudio.kadeproject.utils.invisible
import id.co.imastudio.kadeproject.utils.visible
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
class NextMatchFragment : Fragment(), AnkoComponent<Context>, MatchView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
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

    private lateinit var spinner: Spinner
    private lateinit var leagueId: String
    
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var events: MutableList<EventsItem> = mutableListOf()

    private lateinit var presenter: MatchPresenter
    private lateinit var adapterMatch: MatchAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapterMatch = MatchAdapter(events);
        listEvent.adapter = adapterMatch

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = MatchPresenter(this, request, gson)
        presenter.getNextEvent("4328")

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                leagueName = spinner.selectedItem.toString()

                if (spinner.selectedItemPosition > 4){
                    leagueId = (4328 + spinner.selectedItemPosition + 1).toString()
                } else {
                    leagueId = (4328 + spinner.selectedItemPosition).toString()
                }
                presenter.getNextEvent(leagueId)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        
        swipeRefresh.onRefresh {
            presenter.getNextEvent(leagueId)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(
                R.menu.main_menu,
                menu
        )

        val searchView = menu?.findItem(R.id.searchMenu)?.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    presenter.searchMatch(query)
                    d("cariawal", query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        searchView.setOnCloseListener {
            progressBar.invisible()
            false
        }
    }

}

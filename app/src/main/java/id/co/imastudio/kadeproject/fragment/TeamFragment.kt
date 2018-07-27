package id.co.imastudio.kadeproject.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.Gson
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.R.array.league
import id.co.imastudio.kadeproject.R.color.colorAccent
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.home.TeamAdapter
import id.co.imastudio.kadeproject.home.TeamPresenter
import id.co.imastudio.kadeproject.home.TeamView
import id.co.imastudio.kadeproject.model.Team
import id.co.imastudio.kadeproject.utils.invisible
import id.co.imastudio.kadeproject.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {

    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: TeamAdapter


    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var teams: MutableList<Team> = mutableListOf(
    )

    private lateinit var presenter: TeamPresenter


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

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapter = TeamAdapter(teams)
        listTeam.adapter = adapter

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = TeamPresenter(this, request, gson)

        //spinner
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }


    //imp mainView
    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
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

        searchView.setOnSearchClickListener {
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    presenter.searchTeam(query)
                    Log.d("cariawal", query)
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
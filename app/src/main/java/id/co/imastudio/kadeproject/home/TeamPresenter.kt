package id.co.imastudio.kadeproject.home

import android.util.Log.d
import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.TeamResponse
import id.co.imastudio.kadeproject.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )
            }
            d("tesdulu", data.await().teams[0].strTeam)
            view.hideLoading()
            view.showTeamList(data.await().teams)

        }
    }

    fun searchTeam(query: String) {
        d("hasilcari","masuk presenter")

        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.searchTeam(query)),
                        TeamResponse::class.java
                )
            }

            d("hasilcari",data.await().teams[0].strTeam)
            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }

}


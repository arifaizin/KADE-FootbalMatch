package id.co.imastudio.kadeproject

import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventsResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

//    fun getTeamList(league: String?) {
//        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                    .doRequest(TheSportDBApi.getTeams(league)),
//                    TeamResponse::class.java
//            )
//
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.teams)
//            }
//        }
//    }

    fun getNextEvent(id: Int) {
        view.showLoading()
        var data : EventsResponse
        doAsync {
            if (id == 0){
                data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPreviousMatch()),
                        EventsResponse::class.java
                )
            } else {
                data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch()),
                        EventsResponse::class.java
                )
            }


            uiThread {
                view.hideLoading()
                view.showMatchList(data.events)
            }
        }
    }
}


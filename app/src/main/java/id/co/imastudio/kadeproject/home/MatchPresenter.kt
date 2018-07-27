package id.co.imastudio.kadeproject.home

import android.util.Log.d
import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventResponse
import id.co.imastudio.kadeproject.model.EventsResponse
import id.co.imastudio.kadeproject.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextEvent(leagueId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(leagueId)),
                        EventsResponse::class.java
                )
            }

            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }

    fun getPreviousEvent(leagueId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPreviousMatch(leagueId)),
                        EventsResponse::class.java
                )
            }

            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }

    fun searchMatch(query: String) {
        d("hasilcari","masuk presenter")

        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.searchMatch(query)),
                        EventResponse::class.java
                )
            }

            d("hasilcari",data.await().events[0].strEvent)
            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }


}


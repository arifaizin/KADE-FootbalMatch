package id.co.imastudio.kadeproject.home

import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventsResponse
import id.co.imastudio.kadeproject.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class HomePresenter(private val view: HomeView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextEvent() {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNextMatch()),
                        EventsResponse::class.java
                )
            }

            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }

    fun getPreviousEvent() {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPreviousMatch()),
                        EventsResponse::class.java
                )
            }

            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }

}


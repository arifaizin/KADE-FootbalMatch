package id.co.imastudio.kadeproject

import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventsResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getNextEvent(id: Int) {
        view.showLoading()
        if (id == 0) {
            async(UI) {
                val data = bg {
                    gson.fromJson(apiRepository
                            .doRequest(TheSportDBApi.getPreviousMatch()),
                            EventsResponse::class.java
                    )
                }

                view.hideLoading()
                view.showMatchList(data.await().events)
            }
        } else {
            async(UI) {
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
    }
}


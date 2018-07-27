package id.co.imastudio.kadeproject.player

import android.util.Log.d
import com.google.gson.Gson
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.PlayerResponse
import id.co.imastudio.kadeproject.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        d("presenter","masuk presenter" + idTeam)
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(idTeam)),
                        PlayerResponse::class.java
                )
            }
            d("tesdulu", data.await().player[0].strPlayer)
            view.hideLoading()
            view.showPlayerList(data.await().player)

        }
    }

}


package id.co.imastudio.kadeproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventsItem
import id.co.imastudio.kadeproject.model.TeamResponse
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        var dataMatch: List<EventsItem> = intent.getParcelableArrayListExtra("data")
        var posisi = intent.getIntExtra("posisi", 0)

        var idHomeTeam = dataMatch.get(posisi).idHomeTeam
        var idAwayTeam = dataMatch.get(posisi).idAwayTeam


        val imgHome = detail_home_logo
        val imgAway = detail_away_logo

        getBadge(idHomeTeam, posisi, imgHome)
        getBadge(idAwayTeam, posisi, imgAway)


        detail_home_team.text = dataMatch.get(posisi).strHomeTeam
        detail_away_team.text = dataMatch.get(posisi).strAwayTeam
        detail_home_score.text = dataMatch.get(posisi).intHomeScore
        detail_away_score.text = dataMatch.get(posisi).intAwayScore
        detail_match_time.text = dataMatch.get(posisi).dateEvent

        detail_home_shots.text = dataMatch.get(posisi).intHomeShots
        detail_away_shots.text = dataMatch.get(posisi).intAwayShots
        detail_home_yellow.text = dataMatch.get(posisi).strHomeYellowCards
        detail_away_yellow.text = dataMatch.get(posisi).strAwayYellowCards
        detail_home_red.text = dataMatch.get(posisi).strHomeRedCards
        detail_away_red.text = dataMatch.get(posisi).strAwayRedCards

    }

    private fun getBadge(idTeam: String?, posisi: Int, imageView: ImageView) {

        val api = ApiRepository()
        val gson = Gson()

        var data: TeamResponse
        doAsync {

            data = gson.fromJson(api
                    .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                    TeamResponse::class.java
            )

            uiThread {
                var linkBadge = data.teams.get(0).teamBadge
                Picasso.get().load(linkBadge).into(imageView)
            }
        }
    }


}

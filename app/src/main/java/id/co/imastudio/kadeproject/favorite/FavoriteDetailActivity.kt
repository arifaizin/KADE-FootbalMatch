package id.co.imastudio.kadeproject.favorite

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.co.imastudio.kadeproject.R
import id.co.imastudio.kadeproject.R.drawable.ic_add_to_favorites
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.database.Favorite
import id.co.imastudio.kadeproject.database.database
import id.co.imastudio.kadeproject.model.TeamResponse
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var favorite: Favorite

    private lateinit var dataEventId: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val dataMatch: List<Favorite> = intent.getParcelableArrayListExtra("data")
        val posisi = intent.getIntExtra("posisi", 0)

        val idHomeTeam = dataMatch[posisi].idHomeTeam
        val idAwayTeam = dataMatch[posisi].idAwayTeam


        val imgHome = detail_home_logo
        val imgAway = detail_away_logo

        getBadge(idHomeTeam, imgHome)
        getBadge(idAwayTeam, imgAway)

        dataEventId = dataMatch[posisi].idEvent.toString()
        val dataEventDate = dataMatch[posisi].dateEvent
        val dataHomeTeam = dataMatch[posisi].strHomeTeam
        val dataAwayTeam = dataMatch[posisi].strAwayTeam
        val dataHomeScore = dataMatch[posisi].intHomeScore
        val dataAwayScore = dataMatch[posisi].intAwayScore
        val dataHomeShots = dataMatch[posisi].intHomeShots
        val dataAwayShots = dataMatch[posisi].intAwayShots
        val dataHomeGoal = dataMatch[posisi].strHomeGoalDetails?.replace(";".toRegex(), "\n")
        val dataAwayGoal = dataMatch[posisi].strAwayGoalDetails?.replace(";".toRegex(), "\n")
        val dataHomeYellow = dataMatch[posisi].strHomeYellowCards?.replace(";".toRegex(), "\n")
        val dataAwayYellow = dataMatch[posisi].strAwayYellowCards?.replace(";".toRegex(), "\n")
        val dataHomeRed = dataMatch[posisi].strHomeRedCards?.replace(";".toRegex(), "\n")
        val dataAwayRed = dataMatch[posisi].strAwayRedCards?.replace(";".toRegex(), "\n")
        val dataIdHome = dataMatch[posisi].idHomeTeam
        val dataIdAway = dataMatch[posisi].idAwayTeam

        favorite = Favorite(1, dataEventId,
                dataEventDate,
                dataHomeTeam,
                dataAwayTeam,
                dataHomeScore,
                dataAwayScore,
                dataHomeShots,
                dataAwayShots,
                dataHomeGoal,
                dataAwayGoal,
                dataHomeYellow,
                dataAwayGoal,
                dataHomeRed,
                dataAwayRed,
                dataIdHome,
                dataIdAway
        )

        detail_match_time.text = dataEventDate

        detail_home_team.text = dataHomeTeam
        detail_away_team.text = dataAwayTeam
        detail_home_score.text = dataHomeScore
        detail_away_score.text = dataAwayScore

        detail_home_shots.text = dataHomeShots
        detail_away_shots.text = dataAwayShots

        detail_home_goal.text = dataHomeGoal?.replace(";".toRegex(), "\n")
        detail_away_goal.text = dataAwayGoal?.replace(";".toRegex(), "\n")
        detail_home_yellow.text = dataHomeYellow?.replace(";", "\n")
        detail_away_yellow.text = dataAwayYellow?.replace(";".toRegex(), "\n")
        detail_home_red.text = dataHomeRed?.replace(";".toRegex(), "\n")
        detail_away_red.text = dataAwayRed?.replace(";".toRegex(), "\n")


        favoriteState()
        setFavorite()

    }

    private fun getBadge(idTeam: String?, imageView: ImageView) {

        val api = ApiRepository()
        val gson = Gson()

        async(UI) {
            val data = bg {
                gson.fromJson(api
                        .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                        TeamResponse::class.java
                )
            }
            val linkBadge = data.await().teams.get(0).strTeamBadge
            Picasso.get().load(linkBadge).into(imageView)
        }
}


override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.detail_menu, menu)
    menuItem = menu
    setFavorite()

    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.add_to_favorite -> {
            if (isFavorite) removeFromFavorite() else addToFavorite()

            isFavorite = !isFavorite
            setFavorite()
            toast(isFavorite.toString())

            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}

private fun addToFavorite() {
    try {
        database.use {
            insert(Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to favorite.idEvent,
                    Favorite.EVENT_ID to favorite.idEvent,
                    Favorite.EVENT_DATE to favorite.dateEvent,
                    Favorite.HOME_TEAM to favorite.strHomeTeam,
                    Favorite.AWAY_TEAM to favorite.strAwayTeam,
                    Favorite.HOME_SCORE to favorite.intHomeScore,
                    Favorite.AWAY_SCORE to favorite.intAwayScore,
                    Favorite.HOME_SHOTS to favorite.intHomeShots,
                    Favorite.AWAY_SHOTS to favorite.intAwayShots,
                    Favorite.HOME_GOAL to favorite.strHomeGoalDetails,
                    Favorite.AWAY_GOAL to favorite.strAwayGoalDetails,
                    Favorite.HOME_YELLOW to favorite.strHomeYellowCards,
                    Favorite.AWAY_YELLOW to favorite.strAwayYellowCards,
                    Favorite.HOME_RED to favorite.strHomeRedCards,
                    Favorite.AWAY_RED to favorite.strAwayRedCards,
                    Favorite.ID_HOME to favorite.idHomeTeam,
                    Favorite.ID_AWAY to favorite.idAwayTeam

            )
        }
        toast("Added to favorite").show()
    } catch (e: SQLiteConstraintException) {
        toast(e.localizedMessage).show()
    }
}

private fun removeFromFavorite() {
    try {
        database.use {
            delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to dataEventId)
        }
        toast("Removed to favorite").show()
    } catch (e: SQLiteConstraintException) {
        toast(e.localizedMessage).show()
    }
}

private fun setFavorite() {
    if (isFavorite)
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_added)
    else
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
}

private fun favoriteState() {
    database.use {
        val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                        "id" to dataEventId)
        val favorite = result.parseList(classParser<Favorite>())
        if (!favorite.isEmpty()) isFavorite = true
    }
}
}

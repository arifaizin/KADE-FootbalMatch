package id.co.imastudio.kadeproject

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.co.imastudio.kadeproject.R.drawable.ic_add_to_favorites
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.database.Favorite
import id.co.imastudio.kadeproject.database.database
import id.co.imastudio.kadeproject.model.TeamResponse
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var favorite: Favorite

    private lateinit var dataEventId: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        var dataMatch: List<Favorite> = intent.getParcelableArrayListExtra("data")
        var posisi = intent.getIntExtra("posisi", 0)

        var idHomeTeam = dataMatch[posisi].idHomeTeam
        var idAwayTeam = dataMatch[posisi].idAwayTeam


        val imgHome = detail_home_logo
        val imgAway = detail_away_logo

        getBadge(idHomeTeam, imgHome)
        getBadge(idAwayTeam, imgAway)

        dataEventId = dataMatch[posisi].idEvent.toString()
        var dataEventDate = dataMatch[posisi].dateEvent
        var dataHomeTeam = dataMatch[posisi].strHomeTeam
        var dataAwayTeam = dataMatch[posisi].strAwayTeam
        var dataHomeScore = dataMatch[posisi].intHomeScore
        var dataAwayScore = dataMatch[posisi].intAwayScore
        var dataHomeShots = dataMatch[posisi].intHomeShots
        var dataAwayShots = dataMatch[posisi].intAwayShots
        var dataHomeGoal = dataMatch[posisi].strHomeGoalDetails?.replace(";".toRegex(), "\n")
        var dataAwayGoal = dataMatch[posisi].strAwayGoalDetails?.replace(";".toRegex(), "\n")
        var dataHomeYellow = dataMatch[posisi].strHomeYellowCards?.replace(";".toRegex(), "\n")
        var dataAwayYellow = dataMatch[posisi].strAwayYellowCards?.replace(";".toRegex(), "\n")
        var dataHomeRed = dataMatch[posisi].strHomeRedCards?.replace(";".toRegex(), "\n")
        var dataAwayRed = dataMatch[posisi].strAwayRedCards?.replace(";".toRegex(), "\n")
        var dataIdHome = dataMatch[posisi].idHomeTeam
        var dataIdAway = dataMatch[posisi].idAwayTeam

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

    private fun addToFavorite(){
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
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})", 
                        "id" to dataEventId)
            }
            toast("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_added)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to dataEventId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}

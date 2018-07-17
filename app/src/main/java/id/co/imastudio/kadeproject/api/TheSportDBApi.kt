package id.co.imastudio.kadeproject.api

import id.co.imastudio.kadeproject.BuildConfig

object TheSportDBApi {
    fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    //todo 1: isi
    fun getNextMatch(): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=4328"
//        https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328

    }

    fun getPreviousMatch(): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=4328"

    }
}
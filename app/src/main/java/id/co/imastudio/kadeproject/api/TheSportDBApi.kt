package id.co.imastudio.kadeproject.api

import id.co.imastudio.kadeproject.BuildConfig

object TheSportDBApi {
    fun getNextMatch(): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=4328"

    }

    fun getPreviousMatch(): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=4328"

    }

    fun getTeamDetail(idTeam: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id="+idTeam

    }
}
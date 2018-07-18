package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

data class Match(
        @SerializedName("idEvent")
        var eventId: String? = null,

        @SerializedName("strEvent")
        var eventName: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,

        @SerializedName("dateEvent")
        var eventDate: String? = null



)
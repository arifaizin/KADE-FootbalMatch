package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

//todo 2 model
data class Team(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)
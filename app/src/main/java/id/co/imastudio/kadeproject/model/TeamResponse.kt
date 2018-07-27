package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(

	@field:SerializedName("teams")
	val teams: List<Team>
)
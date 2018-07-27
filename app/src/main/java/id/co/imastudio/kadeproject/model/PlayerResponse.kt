package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(

	@field:SerializedName("player")
	val player: List<PlayersItem>
)
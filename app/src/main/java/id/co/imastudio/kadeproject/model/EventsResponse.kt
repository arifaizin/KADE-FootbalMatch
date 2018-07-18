package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(

	@SerializedName("events")
	val events: List<EventsItem>
)
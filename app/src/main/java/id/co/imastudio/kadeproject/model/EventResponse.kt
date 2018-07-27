package id.co.imastudio.kadeproject.model

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@SerializedName("event")
	val events: List<EventsItem>
)
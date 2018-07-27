package id.co.imastudio.kadeproject.home

import id.co.imastudio.kadeproject.model.EventsItem

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<EventsItem>)
}
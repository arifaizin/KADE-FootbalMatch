package id.co.imastudio.kadeproject.home

import id.co.imastudio.kadeproject.model.EventsItem

interface HomeView {
    fun showLoading()
    fun hideLoading()
//    fun showTeamList(data: List<Team>)
    fun showMatchList(data: List<EventsItem>)
}
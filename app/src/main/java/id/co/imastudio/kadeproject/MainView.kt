package id.co.imastudio.kadeproject

import id.co.imastudio.kadeproject.model.EventsItem

interface MainView {
    fun showLoading()
    fun hideLoading()
//    fun showTeamList(data: List<Team>)
    fun showMatchList(data: List<EventsItem>)
}
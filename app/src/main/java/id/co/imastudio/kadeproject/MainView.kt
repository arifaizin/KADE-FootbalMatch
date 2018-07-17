package id.co.imastudio.kadeproject

import id.co.imastudio.kadeproject.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}
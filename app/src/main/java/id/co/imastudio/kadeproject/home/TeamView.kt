package id.co.imastudio.kadeproject.home

import id.co.imastudio.kadeproject.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}
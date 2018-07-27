package id.co.imastudio.kadeproject.player

import id.co.imastudio.kadeproject.model.PlayersItem

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<PlayersItem>)
}
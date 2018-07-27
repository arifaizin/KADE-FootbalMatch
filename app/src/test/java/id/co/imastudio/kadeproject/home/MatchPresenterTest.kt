package id.co.imastudio.kadeproject.home

import com.google.gson.Gson
import id.co.imastudio.kadeproject.TestContextProvider
import id.co.imastudio.kadeproject.api.ApiRepository
import id.co.imastudio.kadeproject.api.TheSportDBApi
import id.co.imastudio.kadeproject.model.EventsItem
import id.co.imastudio.kadeproject.model.EventsResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextEvent() {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = EventsResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(idLeague)),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getNextEvent(idLeague)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(events)
        Mockito.verify(view).hideLoading()

    }


    @Test
    fun getPreviousEvent() {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = EventsResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPreviousMatch(leagueId)),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getPreviousEvent(leagueName)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(events)
        Mockito.verify(view).hideLoading()

    }

}
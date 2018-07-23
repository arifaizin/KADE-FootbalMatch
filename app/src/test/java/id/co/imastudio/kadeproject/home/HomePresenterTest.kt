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

class HomePresenterTest {

    @Mock
    private
    lateinit var view: HomeView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: HomePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = HomePresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextEvent() {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = EventsResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch()),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getNextEvent()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(events)
        Mockito.verify(view).hideLoading()

    }


    @Test
    fun getPreviousEvent() {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = EventsResponse(events)

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPreviousMatch()),
                EventsResponse::class.java
        )).thenReturn(response)

        presenter.getPreviousEvent()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(events)
        Mockito.verify(view).hideLoading()

    }

}
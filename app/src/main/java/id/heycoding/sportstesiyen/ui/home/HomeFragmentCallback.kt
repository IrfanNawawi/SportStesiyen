package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.data.source.response.EventLeague
import id.heycoding.sportstesiyen.data.source.response.TeamsLeague
import id.heycoding.sportstesiyen.domain.model.Event
import id.heycoding.sportstesiyen.domain.model.News
import id.heycoding.sportstesiyen.domain.model.Teams

interface HomeFragmentCallback {
    fun onDetailTeamsLeague(teamsList: Teams)
    fun onDetailEventLeague(eventList: Event)
    fun onDetailNewsTopHeadlineNews(topHeadlineList: News)
}
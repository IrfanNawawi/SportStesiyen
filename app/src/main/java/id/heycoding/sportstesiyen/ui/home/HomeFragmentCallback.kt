package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.data.source.response.EventLeague
import id.heycoding.sportstesiyen.data.source.response.TeamsLeague

interface HomeFragmentCallback {
    fun onDetailTeamsLeague(teamsList: TeamsLeague)
    fun onDetailEventLeague(eventList: EventLeague)
    fun onDetailNewsTopHeadlineNews(topHeadlineList: Articles)
}
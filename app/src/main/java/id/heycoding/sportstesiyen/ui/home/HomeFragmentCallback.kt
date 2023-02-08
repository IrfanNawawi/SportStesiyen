package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.entity.Articles
import id.heycoding.sportstesiyen.data.entity.EventLeague
import id.heycoding.sportstesiyen.data.entity.TeamsLeague

interface HomeFragmentCallback {
    fun onDetailTeamsLeague(teamsList: TeamsLeague)
    fun onDetailEventLeague(eventList: EventLeague)
    fun onDetailNewsTopHeadlineNews(topHeadlineList: Articles)
}
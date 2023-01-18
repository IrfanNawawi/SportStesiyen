package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.data.remote.response.ArticlesTopHeadline
import id.heycoding.sportstesiyen.data.remote.response.Team

interface HomeFragmentCallback {
    fun onDetailCategory(teamsList: Team, position: Int)
    fun onDetailNewsEverything(everythingList: ArticlesEverything)
    fun onDetailNewsTopHeadlineNews(topHeadlineList: ArticlesTopHeadline)
}
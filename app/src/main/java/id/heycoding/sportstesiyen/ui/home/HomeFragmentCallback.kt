package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.data.remote.response.ArticlesTopHeadline
import id.heycoding.sportstesiyen.data.remote.response.SportsItem

interface HomeFragmentCallback {
    fun onDetailCategory(categoryList: SportsItem, position: Int)
    fun onDetailNewsEverything(everythingList: ArticlesEverything)
    fun onDetailNewsTopHeadlineNews(topHeadlineList: ArticlesTopHeadline)
}
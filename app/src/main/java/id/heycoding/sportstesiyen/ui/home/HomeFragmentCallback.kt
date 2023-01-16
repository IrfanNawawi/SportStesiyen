package id.heycoding.sportstesiyen.ui.home

import id.heycoding.sportstesiyen.data.remote.response.SportsItem

interface HomeFragmentCallback {
    fun onDetailCategory(categoryList: SportsItem, position: Int)
}
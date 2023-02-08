package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague

import androidx.lifecycle.ViewModel
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.DetailTeamsMenu
import id.heycoding.sportstesiyen.utils.DataDummy

class DetailTeamsLeagueViewModel : ViewModel() {
    fun getMenuDetailTeams(): List<DetailTeamsMenu> = DataDummy.generateDummyDetailTeamsMenu()
}
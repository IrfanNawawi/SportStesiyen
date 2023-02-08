package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.heycoding.sportstesiyen.data.entity.TeamsLeague
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.description.DescriptionFragment
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey.JerseyFragment
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news.NewsFragment
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.stadium.StadiumFragment
import id.heycoding.sportstesiyen.utils.ConstSports


class DetailTeamsMenuPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val teams: TeamsLeague? = null
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragDesc = DescriptionFragment()
        fragDesc.arguments = Bundle().apply {
            putParcelable(ConstSports.EXTRA_DATA_TEAMS, teams)
        }
        val fragStadium = StadiumFragment()
        fragStadium.arguments = Bundle().apply {
            putParcelable(ConstSports.EXTRA_DATA_TEAMS, teams)
        }
        val fragJersey = JerseyFragment()
        fragJersey.arguments = Bundle().apply {
            putParcelable(ConstSports.EXTRA_DATA_TEAMS, teams)
        }
        val fragNews = NewsFragment()
        fragNews.arguments = Bundle().apply {
            putParcelable(ConstSports.EXTRA_DATA_TEAMS, teams)
        }

        return when (position) {
            0 -> fragDesc
            1 -> fragStadium
            2 -> fragJersey
            else -> fragNews
        }
    }
}

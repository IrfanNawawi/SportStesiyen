package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.description

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.heycoding.sportstesiyen.data.entity.TeamsLeague
import id.heycoding.sportstesiyen.databinding.FragmentDescriptionBinding
import id.heycoding.sportstesiyen.utils.ConstSports


class DescriptionFragment : Fragment() {

    private var _fragmentDescriptionBinding: FragmentDescriptionBinding? = null
    private val fragmentDescriptionBinding get() = _fragmentDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDescriptionBinding =
            FragmentDescriptionBinding.inflate(layoutInflater, container, false)
        return fragmentDescriptionBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataArguments()
    }

    private fun getDataArguments() {
        val bundle = this.arguments
        if (bundle != null) {
            val dataTeams = bundle.getParcelable<TeamsLeague>(ConstSports.EXTRA_DATA_TEAMS)
            fragmentDescriptionBinding?.apply {
                tvDescTeamsDetail.text = dataTeams?.strDescriptionEN
                tvLeagueTeamsDetailOne.text = "${dataTeams?.strLeague}, "
                tvLeagueTeamsDetailTwo.text = "${dataTeams?.strLeague2}, "
                tvLeagueTeamsDetailThree.text = dataTeams?.strLeague3

                if (dataTeams?.strKitColour1 != null || dataTeams?.strKitColour1 != "") {
                    if (dataTeams?.strKitColour1 != "#FFFFFF") {
                        imgTeamsKitOne.setBackgroundColor(Color.parseColor(dataTeams?.strKitColour1))
                        imgTeamsKitBlankOne.visibility = View.GONE
                    } else {
                        imgTeamsKitOne.visibility = View.GONE
                        imgTeamsKitBlankOne.visibility = View.VISIBLE
                    }
                } else {
                    fmTeamsKitOne.visibility = View.GONE
                }

                if (dataTeams?.strKitColour2 != null || dataTeams?.strKitColour2 != "") {
                    if (dataTeams?.strKitColour2 != "#FFFFFF") {
                        imgTeamsKitSecond.setBackgroundColor(Color.parseColor(dataTeams?.strKitColour2))
                        imgTeamsKitBlankSecond.visibility = View.GONE
                    } else {
                        imgTeamsKitSecond.visibility = View.GONE
                        imgTeamsKitBlankSecond.visibility = View.VISIBLE
                    }
                } else {
                    fmTeamsKitSecond.visibility = View.GONE
                }
            }
        }
    }
}

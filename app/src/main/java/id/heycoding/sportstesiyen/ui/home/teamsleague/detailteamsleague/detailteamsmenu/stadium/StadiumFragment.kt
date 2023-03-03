package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.stadium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.entity.TeamsLeague
import id.heycoding.sportstesiyen.databinding.FragmentStadiumBinding
import id.heycoding.sportstesiyen.utils.ConstSports

class StadiumFragment : Fragment() {

    private var _fragmentStadiumBinding: FragmentStadiumBinding? = null
    private val fragmentStadiumBinding get() = _fragmentStadiumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentStadiumBinding = FragmentStadiumBinding.inflate(layoutInflater, container, false)
        return fragmentStadiumBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataArguments()
    }

    private fun getDataArguments() {
        val bundle = this.arguments
        if (bundle != null) {
            val dataTeams = bundle.getParcelable<TeamsLeague>(ConstSports.EXTRA_DATA_TEAMS)
            fragmentStadiumBinding?.apply {
                Glide.with(this@StadiumFragment).load("${dataTeams?.strStadiumThumb}/preview")
                    .into(imgDetailStadium)
                tvStadiumTeamsDetail.text = dataTeams?.strStadium
                tvLocationStadiumTeamsDetail.text = dataTeams?.strStadiumLocation
                tvCapacityStadiumTeamsDetail.text = "Capacity ${dataTeams?.intStadiumCapacity}"
                tvDescStadiumTeamsDetail.text = dataTeams?.strStadiumDescription
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentStadiumBinding = null
    }

}
package id.heycoding.sportstesiyen.ui.teamsleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.heycoding.sportstesiyen.databinding.FragmentTeamsBinding

class TeamsFragment : Fragment() {

    private var _fragmentTeamsBinding: FragmentTeamsBinding? = null
    private val fragmentTeamsBinding get() = _fragmentTeamsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTeamsBinding = FragmentTeamsBinding.inflate(layoutInflater, container, false)
        return fragmentTeamsBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTeamsBinding = null
    }
}
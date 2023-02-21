package id.heycoding.sportstesiyen.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.heycoding.sportstesiyen.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _fragmentAccountBinding: FragmentAccountBinding? = null
    private val fragmentAccountBinding get() = _fragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = fragmentAccountBinding?.root!!
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentAccountBinding = null
    }
}
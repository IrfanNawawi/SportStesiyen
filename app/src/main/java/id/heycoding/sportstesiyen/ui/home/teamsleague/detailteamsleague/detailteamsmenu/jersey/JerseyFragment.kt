package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.source.response.Equipment
import id.heycoding.sportstesiyen.data.source.response.TeamsLeague
import id.heycoding.sportstesiyen.databinding.FragmentJerseyBinding
import id.heycoding.sportstesiyen.domain.model.Jersey
import id.heycoding.sportstesiyen.utils.ConstSports
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class JerseyFragment : Fragment() {

    private var _fragmentJerseyBinding: FragmentJerseyBinding? = null
    private val fragmentJerseyBinding get() = _fragmentJerseyBinding
    private val jerseyViewModel: JerseyViewModel by activityViewModel()
    private lateinit var jerseyAdapter: JerseyAdapter
    private lateinit var dataJerseyTeams: String
    private val listJersey: MutableList<Jersey> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentJerseyBinding = FragmentJerseyBinding.inflate(layoutInflater, container, false)
        return fragmentJerseyBinding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jerseyAdapter = JerseyAdapter()
        if (isOnline(requireContext())) {
            setupObserver()
            setupUI()
            setupArguments()
        } else {
            showErrorConnection()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    private fun setupArguments() {
        val bundle = this.arguments
        if (bundle != null) {
            val dataTeams = bundle.getParcelable<TeamsLeague>(ConstSports.EXTRA_DATA_TEAMS)
            dataJerseyTeams = dataTeams?.idTeam.toString()

            jerseyViewModel.apply {
                getJerseyTeamsData(dataJerseyTeams)
            }
        }
    }

    private fun showErrorConnection() {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val tvRetryConnectionHome: TextView = view.findViewById(R.id.tv_retry_connection_home)
        tvRetryConnectionHome.setOnClickListener { dialog.cancel() }
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            jerseyViewModel.jerseyUiState.collect {
                when (it) {
                    is JerseyUiState.Success -> {
                        renderListJersey(it.data)
                        showLoading(false)
                    }
                    is JerseyUiState.Error -> {
                        showMessage(it.message)
                        showLoading(false)
                    }
                    is JerseyUiState.Loading -> {
                        showLoading(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupUI() {
        fragmentJerseyBinding?.apply {
            rvJersey.apply {
                layoutManager =
                    GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = jerseyAdapter
                clipToPadding = false
                clipChildren = false
            }
        }
    }

    private fun renderListJersey(listJerseyData: List<Jersey>) {
        listJersey.clear()
        listJersey.addAll(listJerseyData)
        jerseyAdapter.notifyDataSetChanged()
        jerseyAdapter.setJerseyData(listJerseyData)
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            fragmentJerseyBinding?.pgShimmerJersey?.startShimmer()
            fragmentJerseyBinding?.pgShimmerJersey?.visibility = View.VISIBLE
        } else {
            fragmentJerseyBinding?.pgShimmerJersey?.stopShimmer()
            fragmentJerseyBinding?.pgShimmerJersey?.visibility = View.GONE
        }
    }

    @SuppressLint("InflateParams")
    private fun showMessage(message: String?) {
        val view = layoutInflater.inflate(R.layout.popup_error_fetch, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val tvErrorFetch: TextView = view.findViewById(R.id.tv_error_fetch_home)
        tvErrorFetch.text = message

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentJerseyBinding = null
    }
}
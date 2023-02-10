package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.data.source.response.TeamsLeague
import id.heycoding.sportstesiyen.databinding.FragmentNewsBinding
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstSports
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NewsFragment : Fragment() {

    private var _fragmentNewsBinding: FragmentNewsBinding? = null
    private val fragmentNewsBinding get() = _fragmentNewsBinding
    private val newsViewModel: NewsViewModel by activityViewModel()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var dataNewsTeams: String
    private val listNewsEverything: MutableList<Articles> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentNewsBinding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return fragmentNewsBinding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()
        if (isOnline(requireContext())) {
            initViewModel()
            initViews()
            getDataArguments()
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

    private fun getDataArguments() {
        val bundle = this.arguments
        if (bundle != null) {
            val dataTeams = bundle.getParcelable<TeamsLeague>(ConstSports.EXTRA_DATA_TEAMS)
            dataNewsTeams = dataTeams?.strTeam.toString()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val current = LocalDateTime.now().format(formatter)

            newsViewModel.apply {
                getEverythingNewsSportData(dataNewsTeams, Const.FROM_NEWSAPI)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showErrorConnection() {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)

        val tvRetryConnectionHome: TextView = view.findViewById(R.id.tv_retry_connection_home)
        tvRetryConnectionHome.setOnClickListener { dialog.cancel() }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        newsViewModel.apply {
            listEverythingNewsSportData.observe(viewLifecycleOwner) { listNewsData ->
                listNewsEverything.clear()
                listNewsEverything.addAll(listNewsData)
                newsAdapter.notifyDataSetChanged()
                newsAdapter.setEverythingNewsSportData(listNewsData)
            }

            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
        }
    }

    private fun initViews() {
        fragmentNewsBinding?.apply {
            rvNews.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = newsAdapter
                clipToPadding = false
                clipChildren = false
            }
        }
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            fragmentNewsBinding?.pgShimmerNews?.startShimmer()
            fragmentNewsBinding?.pgShimmerNews?.visibility = View.VISIBLE
        } else {
            fragmentNewsBinding?.pgShimmerNews?.stopShimmer()
            fragmentNewsBinding?.pgShimmerNews?.visibility = View.GONE
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
        _fragmentNewsBinding = null
    }
}
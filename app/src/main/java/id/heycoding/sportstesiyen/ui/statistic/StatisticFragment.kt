package id.heycoding.sportstesiyen.ui.statistic

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.entity.Table
import id.heycoding.sportstesiyen.databinding.FragmentStatisticBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StatisticFragment : Fragment() {

    private var _fragmentStatisticBinding: FragmentStatisticBinding? = null
    private val fragmentStatisticBinding get() = _fragmentStatisticBinding
    private val statisticViewModel: StatisticViewModel by activityViewModel()
    private lateinit var statisticAdapter: StatisticAdapter
    private val listStatisticLeague: MutableList<Table> = mutableListOf()

//    private val listIdLeague: MutableList<String> = mutableListOf()
//    private val listLeague: MutableList<String> = mutableListOf()
//    private val listSeason: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentStatisticBinding =
            FragmentStatisticBinding.inflate(layoutInflater, container, false)
        return fragmentStatisticBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statisticViewModel.apply {
            getStatisticLeagueData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statisticAdapter = StatisticAdapter()

        if (isOnline(requireContext())) {
            initViewModel()
            initViews()
        } else {
            showErrorConnection()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    private fun initViewModel() {
        statisticViewModel.apply {
            listStatisticData.observe(viewLifecycleOwner) { listStatisticData ->
                listStatisticLeague.clear()
                listStatisticLeague.addAll(listStatisticData)
                statisticAdapter.notifyDataSetChanged()
                statisticAdapter.setStatisticData(listStatisticData)
            }

//            listLeagueData.observe(viewLifecycleOwner) { league ->
//                listIdLeague.clear()
//                listLeague.clear()
//                listLeague.add(0, "Select a league")
//
//                league.map {
//                    listIdLeague.add(it.idLeague)
//                    listLeague.add(it.strLeague)
//                }
//
//                val adapterLeague = ArrayAdapter(
//                    requireContext(),
//                    android.R.layout.simple_spinner_dropdown_item,
//                    listLeague
//                )
//                adapterLeague.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                fragmentStatisticBinding?.spinnerLeague?.adapter = adapterLeague
//            }

//            listSeasonData.observe(viewLifecycleOwner) { season ->
//                listSeason.clear()
//                listSeason.add(0, "Select a season")
//                season.map {
//                    listSeason.add(it.strSeason)
//                }
//
//                val adapterSeason = ArrayAdapter(
//                    requireContext(),
//                    android.R.layout.simple_spinner_dropdown_item,
//                    listSeason
//                )
//                adapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                fragmentStatisticBinding?.spinnerSeason?.adapter = adapterSeason
//            }

            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
            isError.observe(viewLifecycleOwner) { showMessage(it) }
        }
    }

    private fun initViews() {
        fragmentStatisticBinding?.apply {
            rvStatistic.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = statisticAdapter
                clipToPadding = false
                clipChildren = false
            }

//            spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    parent?.getItemAtPosition(position).toString()
//                    if (parent?.selectedItem == spinnerLeague.selectedItem) {
//                        statisticViewModel.getSeasonData(listIdLeague[position])
//                    }
//                }
//            }

//            spinnerSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    parent?.getItemAtPosition(position).toString()
//                    if (parent?.selectedItem == spinnerSeason.selectedItem) {
//                        statisticViewModel.getStatisticLeagueData(
//                            listIdLeague[position],
//                            listSeason[position]
//                        )
//                    }
//                }
//            }
        }
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
//            fragmentStatisticBinding?.llEmptyData?.visibility = View.VISIBLE
            fragmentStatisticBinding?.fmListStatistic?.visibility = View.INVISIBLE
            fragmentStatisticBinding?.pgShimmerStatistic?.startShimmer()
            fragmentStatisticBinding?.pgShimmerStatistic?.visibility = View.VISIBLE
        } else {
//            fragmentStatisticBinding?.llEmptyData?.visibility = View.INVISIBLE
            fragmentStatisticBinding?.fmListStatistic?.visibility = View.VISIBLE
            fragmentStatisticBinding?.pgShimmerStatistic?.stopShimmer()
            fragmentStatisticBinding?.pgShimmerStatistic?.visibility = View.GONE
        }
    }

    private fun showMessage(message: String?) {
        val view = layoutInflater.inflate(R.layout.popup_error_connection, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val tvErrorFetch: TextView = view.findViewById(R.id.tv_error_connection_home)
        tvErrorFetch.text = message

        dialog.show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentStatisticBinding = null
    }
}
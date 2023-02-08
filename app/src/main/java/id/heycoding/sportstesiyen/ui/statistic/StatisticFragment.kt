package id.heycoding.sportstesiyen.ui.statistic

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
    private val listIdLeague: MutableList<String> = mutableListOf()
    private val listLeague: MutableList<String> = mutableListOf()
    private val listSeason: MutableList<String> = mutableListOf()
    private var leagueSet = ""

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
            getLeagueData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        statisticViewModel.apply {
            listStatisticData.observe(viewLifecycleOwner) { listStatisticData ->
                listStatisticLeague.clear()
                listStatisticLeague.addAll(listStatisticData)
                statisticAdapter.notifyDataSetChanged()
                statisticAdapter.setStatisticData(listStatisticData)
            }

            listLeagueData.observe(viewLifecycleOwner) {
                listLeague.clear()
                listIdLeague.clear()

                listLeague.add("Pilih League")
                listIdLeague.add("0")

                it.map { league ->
                    listLeague.add(league.strLeague)
                    listIdLeague.add(league.idLeague)
                }
            }

            listSeasonData.observe(viewLifecycleOwner) {
                listSeason.clear()
                listSeason.add("Pilih Season")
            }

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

            val adapterLeague = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listLeague
            )
            spinnerLeague.adapter = adapterLeague
            spinnerLeague.count.minus(1)
            spinnerLeague.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        leagueSet = parent.getItemAtPosition(position).toString()
                        Log.d("DAPET YA", "Dapet dong $leagueSet")
                        Toast.makeText(requireContext(), "Pilih $leagueSet", Toast.LENGTH_SHORT)
                            .show()

                        if (parent.selectedItem == spinnerLeague.selectedItem) {
                            statisticViewModel.getSeasonData()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            val adapterSeason = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listSeason
            )
            spinnerSeason.adapter = adapterSeason
            spinnerSeason.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        parent.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            fragmentStatisticBinding?.pgShimmerStatistic?.startShimmer()
            fragmentStatisticBinding?.pgShimmerStatistic?.visibility = View.VISIBLE
        } else {
            fragmentStatisticBinding?.pgShimmerStatistic?.stopShimmer()
            fragmentStatisticBinding?.pgShimmerStatistic?.visibility = View.GONE
        }
    }

    private fun showMessage(message: String?) {
        val view = layoutInflater.inflate(R.layout.popup_error_fetch, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val tvErrorFetch: TextView = view.findViewById(R.id.tv_error_fetch_home)
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
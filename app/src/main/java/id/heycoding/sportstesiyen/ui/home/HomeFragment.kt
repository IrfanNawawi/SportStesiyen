package id.heycoding.sportstesiyen.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.data.source.response.EventLeague
import id.heycoding.sportstesiyen.data.source.response.TeamsLeague
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import id.heycoding.sportstesiyen.domain.model.Event
import id.heycoding.sportstesiyen.domain.model.Jersey
import id.heycoding.sportstesiyen.domain.model.News
import id.heycoding.sportstesiyen.domain.model.Teams
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.home.banner.BannerAdapter
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.ui.home.eventsleague.HomeEventsLeagueAdapter
import id.heycoding.sportstesiyen.ui.home.eventsleague.detaileventsleague.DetailEventsLeagueActivity
import id.heycoding.sportstesiyen.ui.home.teamsleague.HomeTeamsLeagueAdapter
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.DetailTeamsLeagueActivity
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey.JerseyUiState
import id.heycoding.sportstesiyen.ui.home.topheadlinenews.HomeTopHeadlineNewsSportAdapter
import id.heycoding.sportstesiyen.ui.home.topheadlinenews.detailtopheadlinenews.DetailNewsTopHeadlineActivity
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.ConstNews
import id.heycoding.sportstesiyen.utils.ConstSports
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HomeFragment : Fragment(), HomeFragmentCallback {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModel()
    private lateinit var homeEventsLeagueAdapter: HomeEventsLeagueAdapter
    private lateinit var homeTeamsLeagueAdapter: HomeTeamsLeagueAdapter
    private lateinit var homeTopHeadlineNewsSportAdapter: HomeTopHeadlineNewsSportAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private val listSportBannerData = ArrayList<BannerData>()
    private val listEventsLeague: MutableList<Event> = mutableListOf()
    private val listTeamsLeague: MutableList<Teams> = mutableListOf()
    private val listNewsTopHeadline: MutableList<News> = mutableListOf()
    private var sliderhandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupArguments()
        return fragmentHomeBinding?.root
    }

    private fun setupArguments() {
        val userAccount = activity?.intent?.getStringExtra(Const.EXTRA_USER_ACCOUNT)
        fragmentHomeBinding?.tvUsernameHome?.text = userAccount
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.apply {
            getEventLeagueData()
            getTeamsData()
            getTopHeadlineNewsSportData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeEventsLeagueAdapter = HomeEventsLeagueAdapter(this)
        homeTeamsLeagueAdapter = HomeTeamsLeagueAdapter(this)
        homeTopHeadlineNewsSportAdapter = HomeTopHeadlineNewsSportAdapter(this)
        bannerAdapter = BannerAdapter(listSportBannerData)

        if (isOnline(requireContext())) {
            setupObserver()
            setupUI()
            setupIndicator()
            setCurrectIndicator(0)
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
    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.homeUiState.collect {
                when (it) {
                    is HomeUiState.SuccessEvents -> {
                        renderListEventLeague(it.data)
                        showLoading(false)
                    }
                    is HomeUiState.SuccessNews -> {
                        renderListNews(it.data)
                        showLoading(false)
                    }
                    is HomeUiState.SuccessTeams -> {
                        renderListTeamsLeague(it.data)
                        showLoading(false)
                    }
                    is HomeUiState.Error -> {
                        showMessage(it.message)
                        showLoading(false)
                    }
                    is HomeUiState.Loading -> {
                        showLoading(true)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupUI() {
        val onBannerData = homeViewModel.getBannerData()
        bannerAdapter.setBannerData(onBannerData)

        fragmentHomeBinding?.apply {
            // init ViewPager Banner
            vpBannerHome.apply {
                adapter = bannerAdapter

                offscreenPageLimit = 3
                clipToPadding = false
                clipChildren = false

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(30))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - kotlin.math.abs(position)
                    page.scaleY = 0.85f + r * 0.25f
                }

                setPageTransformer(compositePageTransformer)

                fragmentHomeBinding?.vpBannerHome?.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrectIndicator(position)
                        sliderhandler.removeCallbacks(sliderRunnable)
                        sliderhandler.postDelayed(sliderRunnable, 3000)
                    }
                })
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }

            rvEventsLeagueHome.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeEventsLeagueAdapter
                clipToPadding = false
                clipChildren = false
            }

            // init List Sport Category
            rvTeamsLeagueHome.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeTeamsLeagueAdapter
                clipToPadding = false
                clipChildren = false
            }

            // init List News Sport
            rvTopHeadlineNewsSportHome.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeTopHeadlineNewsSportAdapter
                clipToPadding = false
                clipChildren = false

                val snapHelper: SnapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rvTopHeadlineNewsSportHome)
            }

            imgSearchHome.setOnClickListener {
                homeViewModel.doSignOut()
            }
        }
    }

    private fun renderListEventLeague(listEventsData: List<Event>) {
        listEventsLeague.clear()
        listEventsLeague.addAll(listEventsData)
        homeEventsLeagueAdapter.notifyDataSetChanged()
        homeEventsLeagueAdapter.setEventsLeagueData(listEventsData)
    }

    private fun renderListNews(listNewsData: List<News>) {
        listNewsTopHeadline.clear()
        listNewsTopHeadline.addAll(listNewsData)
        homeTopHeadlineNewsSportAdapter.notifyDataSetChanged()
        homeTopHeadlineNewsSportAdapter.setTopHeadlineNewsSportData(listNewsData)
    }

    private fun renderListTeamsLeague(listTeamsData: List<Teams>) {
        listTeamsLeague.clear()
        listTeamsLeague.addAll(listTeamsData)
        homeTeamsLeagueAdapter.notifyDataSetChanged()
        homeTeamsLeagueAdapter.setSportData(listTeamsData)
    }

    private fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(bannerAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                fragmentHomeBinding?.llIndicatorBannerHome?.addView(it)
            }
        }
    }

    private fun setCurrectIndicator(position: Int) {
        val childCount = fragmentHomeBinding?.llIndicatorBannerHome?.childCount
        for (i in 0 until childCount!!) {
            val imageView =
                fragmentHomeBinding?.llIndicatorBannerHome?.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.indicator_active_background
                        )
                    }
                )
            } else {
                imageView.setImageDrawable(
                    context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.indicator_inactive_background
                        )
                    }
                )
            }
        }
    }

    private val sliderRunnable = Runnable {
        fragmentHomeBinding?.vpBannerHome?.currentItem =
            fragmentHomeBinding?.vpBannerHome?.currentItem?.plus(1)!!
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            fragmentHomeBinding?.pgShimmerBannerHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerBannerHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerEventLeagueHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerEventLeagueHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerSportCategoryHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerSportCategoryHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.visibility = View.VISIBLE
        } else {
            fragmentHomeBinding?.pgShimmerBannerHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerBannerHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerEventLeagueHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerEventLeagueHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerSportCategoryHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerSportCategoryHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.visibility = View.GONE
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

    private fun showSignOut() {
        startActivity(Intent(activity, AuthActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        sliderhandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        sliderhandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentHomeBinding = null
    }

    override fun onDetailTeamsLeague(teams: Teams) {
//        val bundle = Bundle()
//        bundle.putParcelable(ConstSports.EXTRA_TEAMS, teams)

//        val intent = Intent(context, DetailTeamsLeagueActivity::class.java)
//        intent.putExtras(bundle)
//        startActivity(intent)
    }

    override fun onDetailEventLeague(eventList: Event) {
//        startActivity(
//            Intent(
//                context,
//                DetailEventsLeagueActivity::class.java
//            ).putExtra(ConstSports.EXTRA_EVENT, eventList)
//        )
    }

    override fun onDetailNewsTopHeadlineNews(topHeadlineList: News) {
//        startActivity(
//            Intent(
//                context,
//                DetailNewsTopHeadlineActivity::class.java
//            )
//                .putExtra(ConstNews.EXTRA_NEWS_TOPHEADLINE, topHeadlineList)
//        )
    }

    override fun onStart() {
        super.onStart()
    }
}
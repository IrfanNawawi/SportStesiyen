package id.heycoding.sportstesiyen.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.data.remote.response.ArticlesTopHeadline
import id.heycoding.sportstesiyen.data.remote.response.Team
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import id.heycoding.sportstesiyen.ui.auth.AuthActivity
import id.heycoding.sportstesiyen.ui.home.banner.BannerAdapter
import id.heycoding.sportstesiyen.ui.home.banner.BannerData
import id.heycoding.sportstesiyen.ui.home.everything.HomeEverythingNewsSportAdapter
import id.heycoding.sportstesiyen.ui.home.teamsleague.HomeTeamsLeagueAdapter
import id.heycoding.sportstesiyen.ui.home.topheadlinenews.HomeTopHeadlineNewsSportAdapter
import id.heycoding.sportstesiyen.ui.newseverything.NewsEverythingActivity
import id.heycoding.sportstesiyen.ui.newstopheadline.NewsTopHeadlineActivity
import id.heycoding.sportstesiyen.utils.ConstNews
import java.lang.Math.abs


class HomeFragment : Fragment(), HomeFragmentCallback {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var homeTeamsLeagueAdapter: HomeTeamsLeagueAdapter
    private lateinit var homeTopHeadlineNewsSportAdapter: HomeTopHeadlineNewsSportAdapter
    private lateinit var homeEverythingNewsSportAdapter: HomeEverythingNewsSportAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private val listSportBannerData = ArrayList<BannerData>()
    private val listTeamsLeague: MutableList<Team> = mutableListOf()
    private val listNewsEverything: MutableList<ArticlesEverything> = mutableListOf()
    private val listNewsTopHeadline: MutableList<ArticlesTopHeadline> = mutableListOf()
    private var sliderhandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeTeamsLeagueAdapter = HomeTeamsLeagueAdapter(this)
        homeTopHeadlineNewsSportAdapter = HomeTopHeadlineNewsSportAdapter(this)
        homeEverythingNewsSportAdapter = HomeEverythingNewsSportAdapter(this)
        bannerAdapter = BannerAdapter(fragmentHomeBinding?.vpSportHome, listSportBannerData)

        if (isOnline(requireContext())) {
            initViewModel()
            initViews()
            setupIndicator()
            setCurrectIndicator(0)
        } else {
            showError()
        }
    }

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

    private fun initViewModel() {
        homeViewModel.apply {

            // fetch API
            doCheckingUser()
            getAllTeamsData("English Premier League")
            getTopHeadlineNewsSportData()
            getEverythingNewsSportData()

            // observe ViewModel
            val onBannerData = getBannerData()
            bannerAdapter.setBannerData(onBannerData)

            listTeamsLeagueData.observe(viewLifecycleOwner) { listTeamsData ->
                listTeamsLeague.clear()
                listTeamsLeague.addAll(listTeamsData)
                homeTeamsLeagueAdapter.notifyDataSetChanged()
                homeTeamsLeagueAdapter.setSportData(listTeamsData)
            }

            listTopHeadlineNewsSportData.observe(viewLifecycleOwner) { listNewsTopHeadlineData ->
                listNewsTopHeadline.clear()
                listNewsTopHeadline.addAll(listNewsTopHeadlineData)
                homeTopHeadlineNewsSportAdapter.notifyDataSetChanged()
                homeTopHeadlineNewsSportAdapter.setTopHeadlineNewsSportData(listNewsTopHeadlineData)
            }

            listEverythingNewsSportData.observe(viewLifecycleOwner) { listNewsEverythingData ->
                listNewsEverything.clear()
                listNewsEverything.addAll(listNewsEverythingData)
                homeEverythingNewsSportAdapter.notifyDataSetChanged()
                homeEverythingNewsSportAdapter.setEverythingNewsSportData(listNewsEverythingData)
            }

            isLoading.observe(viewLifecycleOwner) { showLoading(it) }
            isCheckingAccount.observe(viewLifecycleOwner) {
                if (it != null) {
                    _fragmentHomeBinding?.tvUsernameHome?.text = it
                }
            }
            isValidate.observe(viewLifecycleOwner) { showSignOut() }
        }
    }

    private fun initViews() {
        fragmentHomeBinding?.apply {
            // init ViewPager Banner
            vpSportHome.apply {
                adapter = bannerAdapter

                offscreenPageLimit = 3
                clipToPadding = false
                clipChildren = false

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(30))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - abs(position)
                    page.scaleY = 0.85f + r * 0.25f
                }

                setPageTransformer(compositePageTransformer)

                fragmentHomeBinding?.vpSportHome?.registerOnPageChangeCallback(object :
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

            // init List Sport Category
            rvSportHome.apply {
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

            rvEverythingNewsSportHome.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeEverythingNewsSportAdapter
                clipToPadding = false
                clipChildren = false

                val snapHelper: SnapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rvEverythingNewsSportHome)
            }

            imgSearchHome.setOnClickListener {
                homeViewModel.doSignOut()
            }
        }
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
        fragmentHomeBinding?.vpSportHome?.currentItem =
            fragmentHomeBinding?.vpSportHome?.currentItem?.plus(1)!!
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            fragmentHomeBinding?.pgShimmerBannerHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerBannerHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerSportCategoryHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerSportCategoryHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.visibility = View.VISIBLE

            fragmentHomeBinding?.pgShimmerEverythingNewsHome?.startShimmer()
            fragmentHomeBinding?.pgShimmerEverythingNewsHome?.visibility = View.VISIBLE
        } else {
            fragmentHomeBinding?.pgShimmerBannerHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerBannerHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerSportCategoryHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerSportCategoryHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerTopHeadlineNewsHome?.visibility = View.GONE

            fragmentHomeBinding?.pgShimmerEverythingNewsHome?.stopShimmer()
            fragmentHomeBinding?.pgShimmerEverythingNewsHome?.visibility = View.GONE
        }
    }

    private fun showError() {
        val view = layoutInflater.inflate(R.layout.popup_error_fetch, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)
        dialog.show()
        dialog.setCancelable(false)
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

    override fun onDetailCategory(teams: Team, position: Int) {
//        startActivity(
//            Intent(
//                context,
//                CategoryActivity::class.java
//            )
//                .putExtra(ConstCategory.EXTRA_SPORT, teams)
//                .putExtra(ConstCategory.EXTRA_POSITION, position)
//        )
    }

    override fun onDetailNewsEverything(everythingList: ArticlesEverything) {
        startActivity(
            Intent(
                context,
                NewsEverythingActivity::class.java
            )
                .putExtra(ConstNews.EXTRA_NEWS_EVERYTHING, everythingList)
        )
    }

    override fun onDetailNewsTopHeadlineNews(topHeadlineList: ArticlesTopHeadline) {
        startActivity(
            Intent(
                context,
                NewsTopHeadlineActivity::class.java
            )
                .putExtra(ConstNews.EXTRA_NEWS_TOPHEADLINE, topHeadlineList)
        )
    }

}
package id.heycoding.sportstesiyen.ui.home

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.local.SportsItem
import id.heycoding.sportstesiyen.data.remote.MainWebServices
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import id.heycoding.sportstesiyen.ui.home.banner.ImageAdapter
import id.heycoding.sportstesiyen.ui.home.banner.ImageData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeSportAdapter: HomeSportAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val listSportImage = ArrayList<ImageData>()
    private lateinit var dotsSportImage : ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return fragmentHomeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeSportAdapter = HomeSportAdapter()
        imageAdapter = ImageAdapter()
        initViewModel()
        initViews()
    }

    private fun selectDots(position: Int) {
        for (i in 0 until listSportImage.size) {
            if (i == position) {
                dotsSportImage[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_on_primary))
            } else {
                dotsSportImage[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_on_secondary))
            }
        }
    }

    private fun setDotsIndicator() {
        for (i in 0 until listSportImage.size) {
            dotsSportImage.add(TextView(requireContext()))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dotsSportImage[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dotsSportImage[i].text = Html.fromHtml("&#9679")
            }

            dotsSportImage[i].textSize = 18f
            fragmentHomeBinding?.dotsIndicatorVpSport?.addView(dotsSportImage[i])
        }
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.apply {
            getAllSportsData()
            listSportData.observe(requireActivity()) {
                if (it != null) {
                    homeSportAdapter.setSportData(it)
                    imageAdapter.setSportData(it)
                }
            }
        }
    }

    private fun initViews() {
        fragmentHomeBinding?.apply {
            rvSportHome.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeSportAdapter
            }
            vpSportHome.apply {
                adapter = imageAdapter
                dotsSportImage = ArrayList()
                setDotsIndicator()

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        selectDots(position)
                        super.onPageSelected(position)
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }
}
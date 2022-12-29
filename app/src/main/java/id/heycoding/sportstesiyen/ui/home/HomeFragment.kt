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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import id.heycoding.sportstesiyen.databinding.FragmentHomeBinding
import id.heycoding.sportstesiyen.ui.home.banner.ImageAdapter
import id.heycoding.sportstesiyen.ui.home.banner.ImageData
import id.heycoding.sportstesiyen.ui.home.hotnews.HomeNewsSportAdapter
import id.heycoding.sportstesiyen.ui.home.sportcategory.HomeSportCategoryAdapter

class HomeFragment : Fragment() {

    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var homeSportCategoryAdapter: HomeSportCategoryAdapter
    private lateinit var homeNewsSportAdapter: HomeNewsSportAdapter
    private lateinit var imageAdapter: ImageAdapter
    private val listSportImage = ArrayList<ImageData>()
    private lateinit var dotsSportImage: ArrayList<TextView>

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
        homeSportCategoryAdapter = HomeSportCategoryAdapter()
        homeNewsSportAdapter = HomeNewsSportAdapter()
        imageAdapter = ImageAdapter()
        initViewModel()
        initViews()
    }

    private fun selectDots(position: Int) {
        for (i in 0 until listSportImage.size) {
            if (i == position) {
                dotsSportImage[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.google.android.material.R.color.design_default_color_on_primary
                    )
                )
            } else {
                dotsSportImage[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.google.android.material.R.color.design_default_color_on_secondary
                    )
                )
            }
        }
    }

    private fun setDotsIndicator() {
        for (i in 0 until listSportImage.size) {
            dotsSportImage.add(TextView(requireContext()))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dotsSportImage[i].text =
                    Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dotsSportImage[i].text = Html.fromHtml("&#9679")
            }

            dotsSportImage[i].textSize = 18f
            fragmentHomeBinding?.dotsIndicatorVpSport?.addView(dotsSportImage[i])
        }
    }

    private fun initViewModel() {
        homeViewModel.apply {
            // fetch API
            getAllSportsData()
            getNewsSportData()

            // observe ViewModel
            listSportData.observe(viewLifecycleOwner) {
                if (it != null) {
                    homeSportCategoryAdapter.setSportData(it)
                    imageAdapter.setSportData(it)
                }
            }
            
            listNewsSportData.observe(viewLifecycleOwner) {
                if (it != null) {
                    homeNewsSportAdapter.setNewsSportData(it)
                }
            }
        }
    }

    private fun initViews() {
        fragmentHomeBinding?.apply {
            // init ViewPager Banner
            vpSportHome.apply {
                adapter = imageAdapter
                dotsSportImage = ArrayList()
                setDotsIndicator()

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        selectDots(position)
                        super.onPageSelected(position)
                    }
                })
            }

            // init List Sport Category
            rvSportHome.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeSportCategoryAdapter
            }

            // init Lits News Sport
            rvNewsSportHome.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeNewsSportAdapter
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
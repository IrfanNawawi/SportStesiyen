package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.heycoding.sportstesiyen.R
import id.heycoding.sportstesiyen.data.entity.TeamsLeague
import id.heycoding.sportstesiyen.databinding.ActivityDetailTeamsLeagueBinding
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.DetailTeamsMenuPagerAdapter
import id.heycoding.sportstesiyen.utils.ConstSports
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailTeamsLeagueActivity : AppCompatActivity() {
    private var _activityDetailTeamsLeagueBinding: ActivityDetailTeamsLeagueBinding? = null
    private val activityDetailTeamsLeagueBinding get() = _activityDetailTeamsLeagueBinding
    private val detailTeamsLeagueViewModel: DetailTeamsLeagueViewModel by viewModel()
    private lateinit var detailTeamsLeagueAdapter: DetailTeamsLeagueAdapter
    private val tabTitles = mutableListOf(
        "Description", "Stadium", "Jersey", "News"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailTeamsLeagueBinding = ActivityDetailTeamsLeagueBinding.inflate(layoutInflater)
        setContentView(activityDetailTeamsLeagueBinding?.root)

        setSupportActionBar(activityDetailTeamsLeagueBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (supportActionBar != null) {
            supportActionBar?.title = ""
        }

        detailTeamsLeagueAdapter = DetailTeamsLeagueAdapter()

        setupObserve()
        setupUI()
    }

    private fun setupObserve() {
        detailTeamsLeagueViewModel.apply {
            val menuDetailTeams = getMenuDetailTeams()
            detailTeamsLeagueAdapter.setMenuDetailTeamsData(menuDetailTeams)
        }
    }

    private fun setupUI() {
        val extras = intent.extras
        if (extras != null) {
            val getData = extras.getParcelable<TeamsLeague>(ConstSports.EXTRA_TEAMS)

            activityDetailTeamsLeagueBinding?.apply {
                Glide.with(this@DetailTeamsLeagueActivity)
                    .load("${getData?.strTeamFanart3}/preview").into(imgBannerTeams)
                Glide.with(this@DetailTeamsLeagueActivity).load(getData?.strTeamBadge)
                    .into(imgLogoTeams)
                tvTeams.text = getData?.strTeam
                tvTeamsAlternate.text = getData?.strAlternate
                tvStadiumTeams.text = "${getData?.strStadium}, "
                tvCountryTeams.text = getData?.strCountry
                tvDateTeams.text = getData?.intFormedYear

                val adapter =
                    DetailTeamsMenuPagerAdapter(supportFragmentManager, lifecycle, getData)
                vpMenuDetailTeams.adapter = adapter

                TabLayoutMediator(
                    tlMenuDetailTeams,
                    vpMenuDetailTeams
                ) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()

                for (i in 0..tabTitles.size) {
                    val textTitle = LayoutInflater.from(this@DetailTeamsLeagueActivity)
                        .inflate(R.layout.component_tab_title, null) as TextView
                    tlMenuDetailTeams.getTabAt(i)?.customView = textTitle
                }

                appbarDetailTeams.addOnOffsetChangedListener(object :
                    AppBarLayout.OnOffsetChangedListener {
                    var isShow = false
                    var scrollRange = -1
                    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.totalScrollRange
                        }
                        if (scrollRange + verticalOffset == 0) {
                            tvTitleToolbar.text = getData?.strTeam
                            clDetailTeams.visibility = View.GONE
                            isShow = true
                        } else if (isShow) {
                            tvTitleToolbar.text = ""
                            clDetailTeams.visibility = View.VISIBLE
                            isShow = false
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailTeamsLeagueBinding = null
    }

}
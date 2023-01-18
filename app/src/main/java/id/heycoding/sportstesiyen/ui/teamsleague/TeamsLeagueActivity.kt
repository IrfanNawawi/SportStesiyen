package id.heycoding.sportstesiyen.ui.teamsleague

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.heycoding.sportstesiyen.databinding.ActivityTeamsLeagueBinding
import id.heycoding.sportstesiyen.utils.ConstCategory

class TeamsLeagueActivity : AppCompatActivity() {

    private var _activityTeamsLeagueBinding: ActivityTeamsLeagueBinding? = null
    private val activityTeamsLeagueBinding get() = _activityTeamsLeagueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityTeamsLeagueBinding = ActivityTeamsLeagueBinding.inflate(layoutInflater)
        setContentView(activityTeamsLeagueBinding?.root)
        setSupportActionBar(activityTeamsLeagueBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initIntentData()
    }

    private fun initIntentData() {
        val extras = intent.extras
        if (extras != null) {
//            val listSports: List<SportsItem> =
//                extras.getSerializable(ConstCategory.EXTRA_SPORT) as List<SportsItem>
//            val positionSports = extras.getInt(ConstCategory.EXTRA_POSITION, 0)
//
//            _activityTeamsLeagueBinding?.vpCategory?.adapter =
//                TeamsLeagueAdapterViewPager(listSports, supportFragmentManager, lifecycle)
//            _activityTeamsLeagueBinding?.vpCategory?.currentItem = positionSports
//
//            TabLayoutMediator(
//                _activityTeamsLeagueBinding!!.tablayoutCategory,
//                _activityTeamsLeagueBinding!!.vpCategory
//            ) { tab, position ->
//                tab.text = listSports[position].strSport
//            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityTeamsLeagueBinding = null
    }
}
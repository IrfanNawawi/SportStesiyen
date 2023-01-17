package id.heycoding.sportstesiyen.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.databinding.ActivityCategoryBinding
import id.heycoding.sportstesiyen.utils.ConstCategory

class CategoryActivity : AppCompatActivity() {

    private var _activityCategoryBinding: ActivityCategoryBinding? = null
    private val activityCategoryBinding get() = _activityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityCategoryBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(activityCategoryBinding?.root)
        setSupportActionBar(activityCategoryBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initIntentData()
    }

    private fun initIntentData() {
        val extras = intent.extras
        if (extras != null) {
            val listSports: List<SportsItem> = extras.getSerializable(ConstCategory.EXTRA_SPORT) as List<SportsItem>
            val positionSports = extras.getInt(ConstCategory.EXTRA_POSITION, 0)

            activityCategoryBinding?.vpCategory?.adapter =
                CategoryAdapterViewPager(listSports, supportFragmentManager, lifecycle)
            activityCategoryBinding?.vpCategory?.currentItem = positionSports

            TabLayoutMediator(activityCategoryBinding!!.tablayoutCategory, activityCategoryBinding!!.vpCategory) { tab, position ->
                tab.text = listSports[position].strSport
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityCategoryBinding = null
    }
}
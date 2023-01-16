package id.heycoding.sportstesiyen.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.heycoding.sportstesiyen.data.remote.response.SportsItem

class CategoryAdapterViewPager(
    private val sports: List<SportsItem>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = sports.size

    override fun createFragment(position: Int): Fragment {
        val fragment = CategoryFragment()
        val args = Bundle()
        args.putString("EXTRA_SPORT", sports[position].strSport)
        fragment.arguments = args
        return fragment
    }
}

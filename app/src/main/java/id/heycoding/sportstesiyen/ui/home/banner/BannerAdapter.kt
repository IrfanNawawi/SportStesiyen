package id.heycoding.sportstesiyen.ui.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.databinding.ListSliderSportHomeBinding
import id.heycoding.sportstesiyen.ui.home.sportcategory.HomeSportCategoryCallback
import id.heycoding.sportstesiyen.utils.Const

class BannerAdapter(
    private val viewPager2: ViewPager2?,
    private val listSportBannerData: ArrayList<SportsItem>
) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListSliderSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sport: SportsItem) {
            binding.apply {
                Glide.with(itemView.context).load(sport.strSportThumb).into(imgSliderHome)
            }
        }
    }

    fun setSportData(sport: List<SportsItem>) {
        val diffCallback = HomeSportCategoryCallback(listSportBannerData, sport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listSportBannerData.clear()
        listSportBannerData.addAll(sport)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListSliderSportHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSportBannerData[position])
        if (position == listSportBannerData.size - 2) {
            viewPager2?.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return if (listSportBannerData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listSportBannerData.size
        }
    }

    private val runnable = Runnable {
        listSportBannerData.addAll(listSportBannerData)
        notifyDataSetChanged()
    }
}
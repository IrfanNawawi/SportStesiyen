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
    private val listSportBannerData: ArrayList<BannerData>
) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListSliderSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: BannerData) {
            binding.apply {
                Glide.with(itemView.context).load(banner.imgUrl).into(imgSliderHome)
            }
        }
    }

    fun setBannerData(banner: List<BannerData>) {
        listSportBannerData.clear()
        listSportBannerData.addAll(banner)
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
    }

    override fun getItemCount(): Int = listSportBannerData.size
}
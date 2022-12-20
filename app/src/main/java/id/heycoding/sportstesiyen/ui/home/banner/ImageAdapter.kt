package id.heycoding.sportstesiyen.ui.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.local.SportsItem
import id.heycoding.sportstesiyen.databinding.ListSliderSportHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeSportCallback

class ImageAdapter :RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val listSportBannerData = ArrayList<SportsItem>()
    private val limit = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListSliderSportHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSportBannerData[position])
    }

    override fun getItemCount(): Int {
        return if(listSportBannerData.size > limit){
            limit
        } else {
            listSportBannerData.size
        }
    }

    inner class ViewHolder(private val binding: ListSliderSportHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(sport: SportsItem) {
            binding.apply {
                Glide.with(itemView.context).load(sport.strSportThumb).into(imgSliderHome)
            }
        }
    }

    fun setSportData(sport: List<SportsItem>) {
        val diffCallback = HomeSportCallback(listSportBannerData, sport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listSportBannerData.clear()
        listSportBannerData.addAll(sport)
        diffResult.dispatchUpdatesTo(this)
    }
}
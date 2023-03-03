package id.heycoding.sportstesiyen.ui.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.databinding.ItemSliderSportHomeBinding

class BannerAdapter(
    private val listSportBannerData: ArrayList<BannerData>
) :
    RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSliderSportHomeBinding) :
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
            ItemSliderSportHomeBinding.inflate(
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
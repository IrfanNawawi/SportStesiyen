package id.heycoding.sportstesiyen.ui.home.sportcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.SportsItem
import id.heycoding.sportstesiyen.databinding.ItemSportHomeBinding
import id.heycoding.sportstesiyen.utils.Const

class HomeSportCategoryAdapter : RecyclerView.Adapter<HomeSportCategoryAdapter.ViewHolder>() {
    private val listSportCategoryData = ArrayList<SportsItem>()

    inner class ViewHolder(private val binding: ItemSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sport: SportsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(sport.strSportThumb)
                    .into(imgSportHome)

                tvSportHome.text = sport.strSport
            }
        }
    }

    fun setSportData(sport: List<SportsItem>) {
        val diffCallback = HomeSportCategoryCallback(listSportCategoryData, sport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listSportCategoryData.clear()
        listSportCategoryData.addAll(sport)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSportHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSportCategoryData[position])
    }

    override fun getItemCount(): Int {
        return if (listSportCategoryData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listSportCategoryData.size
        }
    }
}
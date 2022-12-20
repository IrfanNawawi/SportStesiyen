package id.heycoding.sportstesiyen.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.local.SportsItem
import id.heycoding.sportstesiyen.databinding.ItemSportHomeBinding

class HomeSportAdapter : RecyclerView.Adapter<HomeSportAdapter.ViewHolder>() {
    private val listSportData = ArrayList<SportsItem>()
    private val limit = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSportHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSportData[position])
    }

    override fun getItemCount(): Int {
        return if(listSportData.size > limit){
            limit
        } else {
            listSportData.size
        }
    }

    inner class ViewHolder(private val binding: ItemSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sport: SportsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(sport.strSportIconGreen)
                    .into(imgSportHome)

                tvSportHome.text = sport.strSport
            }
        }
    }

    fun setSportData(sport: List<SportsItem>) {
        val diffCallback = HomeSportCallback(listSportData, sport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listSportData.clear()
        listSportData.addAll(sport)
        diffResult.dispatchUpdatesTo(this)
    }
}
package id.heycoding.sportstesiyen.ui.home.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.local.SportsItem
import id.heycoding.sportstesiyen.databinding.ListSliderSportHomeBinding

class ImageAdapter(private val items:List<ImageData>):RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val limit = 5

    inner class ViewHolder(itemView: ListSliderSportHomeBinding): RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(data: ImageData) {
            with(binding) {
                Glide.with(itemView).load(data.imgUrl).into(imgSliderHome)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListSliderSportHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return if(items.size > limit){
            limit
        } else {
            items.size
        }
    }
}
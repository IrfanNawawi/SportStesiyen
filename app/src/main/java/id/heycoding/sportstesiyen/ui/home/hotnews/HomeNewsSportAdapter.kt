package id.heycoding.sportstesiyen.ui.home.hotnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.ArticlesItem
import id.heycoding.sportstesiyen.databinding.ItemNewsSportHomeBinding
import id.heycoding.sportstesiyen.utils.Helper

class HomeNewsSportAdapter : RecyclerView.Adapter<HomeNewsSportAdapter.ViewHolder>() {
    private val listNewsSportData = ArrayList<ArticlesItem>()
//    private val limit = 5

    inner class ViewHolder(private val binding: ItemNewsSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsSport: ArticlesItem) {
            binding.apply {
                Glide.with(itemView.context).load(newsSport.urlToImage).into(imgNewsHome)
                tvTitleNewsHome.text = newsSport.title
                tvDateNewsHome.text = Helper.timeAgo(newsSport.publishedAt)
                tvSourceNewsHome.text = newsSport.source?.name
            }
        }
    }

    fun setNewsSportData(newsSport: List<ArticlesItem>) {
        val diffCallback = HomeNewsSportCallback(listNewsSportData, newsSport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listNewsSportData.clear()
        listNewsSportData.addAll(newsSport)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsSportHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNewsSportData[position])
    }

    override fun getItemCount(): Int {
        return listNewsSportData.size
//        return if (listNewsSportData.size > limit) {
//            limit
//        } else {
//            listNewsSportData.size
//        }
    }
}
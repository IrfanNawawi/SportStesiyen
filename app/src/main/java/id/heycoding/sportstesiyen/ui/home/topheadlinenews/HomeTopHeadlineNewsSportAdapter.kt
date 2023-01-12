package id.heycoding.sportstesiyen.ui.home.topheadlinenews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.ArticlesTopHeadline
import id.heycoding.sportstesiyen.databinding.ItemNewsSportHomeBinding
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.Helper

class HomeTopHeadlineNewsSportAdapter : RecyclerView.Adapter<HomeTopHeadlineNewsSportAdapter.ViewHolder>() {
    private val listTopHeadlineNewsSportData = ArrayList<ArticlesTopHeadline>()

    inner class ViewHolder(private val binding: ItemNewsSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topHeadlineNewsSport: ArticlesTopHeadline) {
            binding.apply {
                Glide.with(itemView.context).load(topHeadlineNewsSport.urlToImage).into(imgNewsHome)
                tvTitleNewsHome.text = topHeadlineNewsSport.title
                tvDateNewsHome.text = Helper.timeAgo(topHeadlineNewsSport.publishedAt)
                tvSourceNewsHome.text = topHeadlineNewsSport.source?.name
            }
        }
    }

    fun setTopHeadlineNewsSportData(topHeadlineNewsSport: List<ArticlesTopHeadline>) {
        val diffCallback = HomeTopHeadlineNewsSportCallback(listTopHeadlineNewsSportData, topHeadlineNewsSport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listTopHeadlineNewsSportData.clear()
        listTopHeadlineNewsSportData.addAll(topHeadlineNewsSport)
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
        holder.bind(listTopHeadlineNewsSportData[position])
    }

    override fun getItemCount(): Int {
        return if (listTopHeadlineNewsSportData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listTopHeadlineNewsSportData.size
        }
    }
}
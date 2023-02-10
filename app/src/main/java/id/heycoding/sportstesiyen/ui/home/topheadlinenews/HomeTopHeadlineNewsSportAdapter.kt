package id.heycoding.sportstesiyen.ui.home.topheadlinenews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.databinding.ItemNewsSportHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeFragmentCallback
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.Helper

class HomeTopHeadlineNewsSportAdapter(private val callback: HomeFragmentCallback) :
    RecyclerView.Adapter<HomeTopHeadlineNewsSportAdapter.ViewHolder>() {
    private val listTopHeadlineNewsSportData = ArrayList<Articles>()

    inner class ViewHolder(private val binding: ItemNewsSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topHeadlineNewsSport: Articles) {
            binding.apply {
                Glide.with(itemView.context).load(topHeadlineNewsSport.urlToImage).into(imgNewsHome)
                tvTitleNewsHome.text = topHeadlineNewsSport.title
                tvDateNewsHome.text = Helper.timeAgo(topHeadlineNewsSport.publishedAt)
                tvSourceNewsHome.text = topHeadlineNewsSport.source?.name

                itemView.setOnClickListener {
                    callback.onDetailNewsTopHeadlineNews(
                        topHeadlineNewsSport
                    )
                }
            }
        }
    }

    fun setTopHeadlineNewsSportData(topHeadlineNewsSport: List<Articles>) {
        val diffCallback =
            HomeTopHeadlineNewsSportCallback(listTopHeadlineNewsSportData, topHeadlineNewsSport)
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
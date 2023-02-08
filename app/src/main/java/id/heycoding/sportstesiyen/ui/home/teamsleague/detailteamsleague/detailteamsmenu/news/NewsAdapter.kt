package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.entity.Articles
import id.heycoding.sportstesiyen.databinding.ItemNewsTeamsDetailBinding
import id.heycoding.sportstesiyen.ui.home.topheadlinenews.HomeTopHeadlineNewsSportCallback
import id.heycoding.sportstesiyen.utils.Helper

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val listEverythingNewsSportData = ArrayList<Articles>()

    inner class ViewHolder(private val binding: ItemNewsTeamsDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(everythingNewsSport: Articles) {
            binding.apply {
                Glide.with(itemView.context).load(everythingNewsSport.urlToImage).into(imgNewsHome)
                tvTitleNewsHome.text = everythingNewsSport.title
                tvDateNewsHome.text = Helper.timeAgo(everythingNewsSport.publishedAt)
                tvSourceNewsHome.text = everythingNewsSport.source?.name
            }
        }
    }

    fun setEverythingNewsSportData(everythingNewsSport: List<Articles>) {
        val diffCallback =
            NewsCallback(listEverythingNewsSportData, everythingNewsSport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listEverythingNewsSportData.clear()
        listEverythingNewsSportData.addAll(everythingNewsSport)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsTeamsDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEverythingNewsSportData[position])
    }

    override fun getItemCount(): Int = listEverythingNewsSportData.size
}
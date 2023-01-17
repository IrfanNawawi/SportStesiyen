package id.heycoding.sportstesiyen.ui.home.everything

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.ArticlesEverything
import id.heycoding.sportstesiyen.databinding.ItemNewsSportHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeFragmentCallback
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.Helper

class HomeEverythingNewsSportAdapter(private val callback: HomeFragmentCallback) :
    RecyclerView.Adapter<HomeEverythingNewsSportAdapter.ViewHolder>() {
    private val listEverythingNewsSportData = ArrayList<ArticlesEverything>()

    inner class ViewHolder(private val binding: ItemNewsSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(everythingNewsSport: ArticlesEverything) {
            binding.apply {
                Glide.with(itemView.context).load(everythingNewsSport.urlToImage).into(imgNewsHome)
                tvTitleNewsHome.text = everythingNewsSport.title
                tvDateNewsHome.text = Helper.timeAgo(everythingNewsSport.publishedAt)
                tvSourceNewsHome.text = everythingNewsSport.source?.name

                itemView.setOnClickListener { callback.onDetailNewsEverything(everythingNewsSport) }
            }
        }
    }

    fun setEverythingNewsSportData(everythingNewsSport: List<ArticlesEverything>) {
        val diffCallback =
            HomeEverythingNewsSportCallback(listEverythingNewsSportData, everythingNewsSport)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listEverythingNewsSportData.clear()
        listEverythingNewsSportData.addAll(everythingNewsSport)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsSportHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEverythingNewsSportData[position])
    }

    override fun getItemCount(): Int {
        return if (listEverythingNewsSportData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listEverythingNewsSportData.size
        }
    }
}
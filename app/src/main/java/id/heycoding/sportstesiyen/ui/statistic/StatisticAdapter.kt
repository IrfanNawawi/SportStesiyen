package id.heycoding.sportstesiyen.ui.statistic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.databinding.ItemHeaderStatisticBinding
import id.heycoding.sportstesiyen.databinding.ItemStatisticBinding
import id.heycoding.sportstesiyen.domain.model.Statistic

class StatisticAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TYPE_HEADER = 0
    private var TYPE_ITEM = 1
    private val listStatisticData = ArrayList<Statistic>()

    class HeaderViewHolder(private val binding: ItemHeaderStatisticBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindHeader() {
            binding.tvPositionStatistic
            binding.tvTeamsStatistic
            binding.tvPlayedStatistic
            binding.tvWinStatistic
            binding.tvDrawStatistic
            binding.tvLoseStatistic
            binding.tvGoalDifferentStatistic
            binding.tvPointStatistic
        }
    }

    inner class ViewHolder(private val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(statistic: Statistic) {
            binding.apply {
                Glide.with(itemView.context).load(statistic.strTeamBadge).into(imgTeamsStatistic)
                tvPositionStatistic.text = statistic.intRank
                tvPlayedStatistic.text = statistic.intPlayed
                tvWinStatistic.text = statistic.intWin
                tvDrawStatistic.text = statistic.intDraw
                tvLoseStatistic.text = statistic.intLoss
                tvGoalDifferentStatistic.text = statistic.intGoalDifference
                tvPointStatistic.text = statistic.intPoints
            }
        }
    }

    fun setStatisticData(statistic: List<Statistic>) {
        val diffCallback = StatisticCallback(listStatisticData, statistic)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listStatisticData.clear()
        listStatisticData.addAll(statistic)
        diffResult.dispatchUpdatesTo(this@StatisticAdapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val itemHeaderStatisticBinding =
                ItemHeaderStatisticBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return HeaderViewHolder(itemHeaderStatisticBinding)
        }
        val itemStatisticBinding = ItemStatisticBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemStatisticBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bindHeader()
        } else if (holder is ViewHolder) {
            holder.bindItem(listStatisticData[position.minus(1)])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    override fun getItemCount(): Int = listStatisticData.size.plus(1)
}
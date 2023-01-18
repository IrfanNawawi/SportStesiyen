package id.heycoding.sportstesiyen.ui.home.teamsleague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.Team
import id.heycoding.sportstesiyen.databinding.ItemSportHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeFragmentCallback
import id.heycoding.sportstesiyen.utils.Const

class HomeTeamsLeagueAdapter(private val callback: HomeFragmentCallback) :
    RecyclerView.Adapter<HomeTeamsLeagueAdapter.ViewHolder>() {
    private val listTeamsLeagueData = ArrayList<Team>()

    inner class ViewHolder(private val binding: ItemSportHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(teams: Team) {
            binding.apply {
                Glide.with(itemView.context)

                    .load(teams.strTeamBadge + "/tiny")
                    .into(imgSportHome)

                tvSportHome.text = teams.strTeam
                tvSportFormatHome.text = teams.intFormedYear

                itemView.setOnClickListener { callback.onDetailCategory(teams, adapterPosition) }
            }
        }
    }

    fun setSportData(teams: List<Team>) {
        val diffCallback = HomeTeamsLeagueCallback(listTeamsLeagueData, teams)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listTeamsLeagueData.clear()
        listTeamsLeagueData.addAll(teams)
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
        holder.bind(listTeamsLeagueData[position])
    }

    override fun getItemCount(): Int {
        return if (listTeamsLeagueData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listTeamsLeagueData.size
        }
    }
}
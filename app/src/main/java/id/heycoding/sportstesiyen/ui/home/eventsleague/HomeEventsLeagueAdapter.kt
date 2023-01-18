package id.heycoding.sportstesiyen.ui.home.eventsleague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.remote.response.Event
import id.heycoding.sportstesiyen.databinding.ItemEventLeagueHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeFragmentCallback
import id.heycoding.sportstesiyen.utils.Const
import id.heycoding.sportstesiyen.utils.Helper

class HomeEventsLeagueAdapter(private val callback: HomeFragmentCallback) :
    RecyclerView.Adapter<HomeEventsLeagueAdapter.ViewHolder>() {
    private val listEventsLeagueData = ArrayList<Event>()

    inner class ViewHolder(private val binding: ItemEventLeagueHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventLeague: Event) {
            binding.apply {
                tvLeagueLatestHome.text = eventLeague.strLeague
                tvDateLatestHome.text = eventLeague.dateEvent
                tvHomeLatest.text = eventLeague.strHomeTeam
                tvAwayLatest.text = eventLeague.strAwayTeam
                tvHomeScore.text = eventLeague.intHomeScore
                tvAwayScore.text = eventLeague.intAwayScore
            }
        }
    }

    fun setEventsLeagueData(eventLeague: List<Event>) {
        val diffCallback = HomeEventsLeagueCallback(listEventsLeagueData, eventLeague)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listEventsLeagueData.clear()
        listEventsLeagueData.addAll(eventLeague)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventLeagueHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listEventsLeagueData[position])
    }

    override fun getItemCount(): Int {
        return if (listEventsLeagueData.size > Const.LIMIT) {
            Const.LIMIT
        } else {
            listEventsLeagueData.size
        }
    }
}
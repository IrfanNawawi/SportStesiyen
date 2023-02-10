package id.heycoding.sportstesiyen.ui.home.eventsleague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sportstesiyen.data.source.response.EventLeague
import id.heycoding.sportstesiyen.databinding.ItemEventLeagueHomeBinding
import id.heycoding.sportstesiyen.ui.home.HomeFragmentCallback
import id.heycoding.sportstesiyen.utils.Const

class HomeEventsLeagueAdapter(private val callback: HomeFragmentCallback) :
    RecyclerView.Adapter<HomeEventsLeagueAdapter.ViewHolder>() {
    private val listEventsLeagueData = ArrayList<EventLeague>()

    inner class ViewHolder(private val binding: ItemEventLeagueHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventLeague: EventLeague) {
            binding.apply {
                tvVenueEvent.text = "${eventLeague.strVenue}, "
                tvCountryEvent.text = eventLeague.strCountry
                tvHomeEvent.text = eventLeague.strHomeTeam
                tvAwayEvent.text = eventLeague.strAwayTeam
                tvHomeScoreEvent.text = eventLeague.intHomeScore
                tvAwayScoreEvent.text = eventLeague.intAwayScore

                itemView.setOnClickListener { callback.onDetailEventLeague(eventLeague) }
            }
        }
    }

    fun setEventsLeagueData(eventLeague: List<EventLeague>) {
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
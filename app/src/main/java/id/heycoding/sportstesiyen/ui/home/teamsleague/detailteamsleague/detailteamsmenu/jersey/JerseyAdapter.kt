package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.jersey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sportstesiyen.data.source.response.Equipment
import id.heycoding.sportstesiyen.databinding.ItemTeamsJerseyBinding

class JerseyAdapter : RecyclerView.Adapter<JerseyAdapter.ViewHolder>() {
    private val listJerseyData = ArrayList<Equipment>()

    inner class ViewHolder(private val binding: ItemTeamsJerseyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jersey: Equipment) {
            binding.apply {
                Glide.with(itemView.context).load(jersey.strEquipment).into(imgJerseyTeam)
                tvJerseyTeam.text = jersey.strUsername
                tvJerseySeasonTeam.text = jersey.strSeason
                tvJerseyTypeTeam.text = jersey.strType
            }
        }
    }

    fun setJerseyData(jersey: List<Equipment>) {
        val diffCallback =
            JerseyCallback(listJerseyData, jersey)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listJerseyData.clear()
        listJerseyData.addAll(jersey)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTeamsJerseyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listJerseyData[position])
    }

    override fun getItemCount(): Int = listJerseyData.size
}
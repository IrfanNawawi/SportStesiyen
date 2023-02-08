package id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sportstesiyen.databinding.ItemMenuDetailTeamsBinding
import id.heycoding.sportstesiyen.ui.home.teamsleague.detailteamsleague.detailteamsmenu.DetailTeamsMenu

class DetailTeamsLeagueAdapter : RecyclerView.Adapter<DetailTeamsLeagueAdapter.ViewHolder>() {
    private val listMenuDetailTeamsData = ArrayList<DetailTeamsMenu>()

    inner class ViewHolder(private val binding: ItemMenuDetailTeamsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menuDetailTeams: DetailTeamsMenu) {
            binding.apply {
                tvMenuDetailTeams.text = menuDetailTeams.menuDetailTeams
            }
        }
    }

    fun setMenuDetailTeamsData(detailTeamsMenu: List<DetailTeamsMenu>) {
        listMenuDetailTeamsData.clear()
        listMenuDetailTeamsData.addAll(detailTeamsMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuDetailTeamsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMenuDetailTeamsData[position])
    }

    override fun getItemCount(): Int = listMenuDetailTeamsData.size
}
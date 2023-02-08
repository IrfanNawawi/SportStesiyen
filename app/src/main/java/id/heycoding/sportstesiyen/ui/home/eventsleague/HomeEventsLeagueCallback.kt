package id.heycoding.sportstesiyen.ui.home.eventsleague

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import id.heycoding.sportstesiyen.data.entity.EventLeague

class HomeEventsLeagueCallback(
    private val oldList: List<EventLeague>,
    private val newList: List<EventLeague>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, value, nameOld) = oldList[oldItemPosition]
        val (_, value1, nameNew) = newList[newItemPosition]
        return nameOld == nameNew && value == value1
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
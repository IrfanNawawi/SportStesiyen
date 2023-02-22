package id.heycoding.sportstesiyen.ui.home.topheadlinenews

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import id.heycoding.sportstesiyen.data.source.response.Articles
import id.heycoding.sportstesiyen.domain.model.News

class HomeTopHeadlineNewsSportCallback(
    private val oldList: List<News>,
    private val newList: List<News>
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
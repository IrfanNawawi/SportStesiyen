package id.heycoding.sportstesiyen.utils

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


object Helper {

    fun timeAgo(publishDate: String?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getDefault()

        val time = sdf.parse(publishDate).time
        val mTime = timestampToMilli(time)
        val currentTime = System.currentTimeMillis()

        if (mTime > currentTime || mTime <= 0) return null

        val timeelapsed = currentTime.minus(mTime)
        val currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]

        val thisDay: Int = SimpleDateFormat("dd", currentLocale).format(currentTime).toInt()
        val thisMonth: Int = SimpleDateFormat("MM", currentLocale).format(currentTime).toInt()
        val thisYear: Int = SimpleDateFormat("yyy", currentLocale).format(currentTime).toInt()
        val agoDay: Int = SimpleDateFormat("dd", currentLocale).format(mTime).toInt()
        val agoMonth: Int = SimpleDateFormat("MM", currentLocale).format(mTime).toInt()
        val agoYear: Int = SimpleDateFormat("yyy", currentLocale).format(mTime).toInt()
        val minutes = (timeelapsed / 60000).toDouble().roundToInt()
        val hours = (timeelapsed / 3600000).toDouble().roundToInt()
        val days = (timeelapsed / 86400000).toDouble().roundToInt()
        val weeks = (timeelapsed / 604800000).toDouble().roundToInt()
        val months = (timeelapsed / 2600640000).toDouble().roundToInt()
        val years = (timeelapsed / 31207680000).toDouble().roundToInt()

        if ((thisYear - agoYear) >= 1) {
            return SimpleDateFormat("MMM dd, yyyy - hh:ma", currentLocale).format(mTime)
        } else if ((thisMonth - agoMonth) >= 1) {
            return SimpleDateFormat("MMM dd", currentLocale).format(mTime)
        } else if (thisMonth == agoMonth && (thisDay - agoDay) == 1) {
            return "Yesterday"
        } else if (timeelapsed <= 60) {
            return "just now"
        } else if (minutes <= 60) {
            return if (minutes == 1) {
                "one minute ago"
            } else {
                "$minutes minutes ago"
            }
        } else if (hours <= 24) {
            return if (hours == 1) {
                "an hour and " + (minutes - 60) + " minutes ago"
            } else {
                "$hours hrs ago"
            }
        } else if (days <= 7) {
            return if (days == 1) {
                "$days day and " + (hours - 24) + " hrs ago"
            } else {
                "$days days ago"
            }
        } else if (weeks <= 4.3) {
            return if (weeks == 1) {
                "a week ago"
            } else {
                "$weeks weeks ago"
            }
        } else if (months <= 12) {
            return if (months == 1) {
                "a month ago"
            } else {
                "$months months ago"
            }
        } else {
            return if (years == 1) {
                "a years ago"
            } else {
                "$years years ago"
            }
        }
    }

    private fun timestampToMilli(time: Long): Long {
        return if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time * 1000
        } else {
            time
        }
    }
}
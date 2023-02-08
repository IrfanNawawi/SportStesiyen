package id.heycoding.sportstesiyen.data.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EventLeagueResponse(
    @SerializedName("events")
    val eventLeagues: List<EventLeague>
)

@Parcelize
data class EventLeague(
    @SerializedName("dateEvent")
    val dateEvent: String? = null,
    @SerializedName("dateEventLocal")
    val dateEventLocal: String? = null,
    @SerializedName("idAPIfootball")
    val idAPIfootball: String? = null,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String? = null,
    @SerializedName("idEvent")
    val idEvent: String? = null,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String? = null,
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("idSoccerXML")
    val idSoccerXML: String? = null,
    @SerializedName("intAwayScore")
    val intAwayScore: String? = null,
    @SerializedName("intHomeScore")
    val intHomeScore: String? = null,
    @SerializedName("intRound")
    val intRound: String? = null,
    @SerializedName("intScore")
    val intScore: String? = null,
    @SerializedName("intScoreVotes")
    val intScoreVotes: String? = null,
    @SerializedName("intSpectators")
    val intSpectators: String? = null,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String? = null,
    @SerializedName("strBanner")
    val strBanner: String? = null,
    @SerializedName("strCity")
    val strCity: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strEvent")
    val strEvent: String? = null,
    @SerializedName("strEventAlternate")
    val strEventAlternate: String? = null,
    @SerializedName("strFanart")
    val strFanart: String? = null,
    @SerializedName("strFilename")
    val strFilename: String? = null,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strLocked")
    val strLocked: String? = null,
    @SerializedName("strMap")
    val strMap: String? = null,
    @SerializedName("strOfficial")
    val strOfficial: String? = null,
    @SerializedName("strPoster")
    val strPoster: String? = null,
    @SerializedName("strPostponed")
    val strPostponed: String? = null,
    @SerializedName("strResult")
    val strResult: String? = null,
    @SerializedName("strSeason")
    val strSeason: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("strSquare")
    val strSquare: String? = null,
    @SerializedName("strStatus")
    val strStatus: String? = null,
    @SerializedName("strTVStation")
    val strTVStation: String? = null,
    @SerializedName("strThumb")
    val strThumb: String? = null,
    @SerializedName("strTime")
    val strTime: String? = null,
    @SerializedName("strTimeLocal")
    val strTimeLocal: String? = null,
    @SerializedName("strTimestamp")
    val strTimestamp: String? = null,
    @SerializedName("strTweet1")
    val strTweet1: String? = null,
    @SerializedName("strTweet2")
    val strTweet2: String? = null,
    @SerializedName("strTweet3")
    val strTweet3: String? = null,
    @SerializedName("strVenue")
    val strVenue: String? = null,
    @SerializedName("strVideo")
    val strVideo: String? = null
) : Parcelable
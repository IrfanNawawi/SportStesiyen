package id.heycoding.sportstesiyen.data.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TeamsLeagueResponse(
    @SerializedName("teams")
    val teamsLeagues: List<TeamsLeague>
)

@Parcelize
data class TeamsLeague(
    @SerializedName("idAPIfootball")
    val idAPIfootball: String? = null,
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("idLeague2")
    val idLeague2: String? = null,
    @SerializedName("idLeague3")
    val idLeague3: String? = null,
    @SerializedName("idLeague4")
    val idLeague4: String? = null,
    @SerializedName("idLeague5")
    val idLeague5: String? = null,
    @SerializedName("idLeague6")
    val idLeague6: String? = null,
    @SerializedName("idLeague7")
    val idLeague7: String? = null,
    @SerializedName("idSoccerXML")
    val idSoccerXML: String? = null,
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("intFormedYear")
    val intFormedYear: String? = null,
    @SerializedName("intLoved")
    val intLoved: String? = null,
    @SerializedName("intStadiumCapacity")
    val intStadiumCapacity: String? = null,
    @SerializedName("strAlternate")
    val strAlternate: String? = null,
    @SerializedName("strCountry")
    val strCountry: String? = null,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: String? = null,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String? = null,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String? = null,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String? = null,
    @SerializedName("strDescriptionHU")
    val strDescriptionHU: String? = null,
    @SerializedName("strDescriptionIL")
    val strDescriptionIL: String? = null,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String? = null,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String? = null,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: String? = null,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String? = null,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: String? = null,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String? = null,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String? = null,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: String? = null,
    @SerializedName("strDivision")
    val strDivision: String? = null,
    @SerializedName("strFacebook")
    val strFacebook: String? = null,
    @SerializedName("strGender")
    val strGender: String? = null,
    @SerializedName("strInstagram")
    val strInstagram: String? = null,
    @SerializedName("strKeywords")
    val strKeywords: String? = null,
    @SerializedName("strKitColour1")
    val strKitColour1: String? = null,
    @SerializedName("strKitColour2")
    val strKitColour2: String? = null,
    @SerializedName("strKitColour3")
    val strKitColour3: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strLeague2")
    val strLeague2: String? = null,
    @SerializedName("strLeague3")
    val strLeague3: String? = null,
    @SerializedName("strLeague4")
    val strLeague4: String? = null,
    @SerializedName("strLeague5")
    val strLeague5: String? = null,
    @SerializedName("strLeague6")
    val strLeague6: String? = null,
    @SerializedName("strLeague7")
    val strLeague7: String? = null,
    @SerializedName("strLocked")
    val strLocked: String? = null,
    @SerializedName("strManager")
    val strManager: String? = null,
    @SerializedName("strRSS")
    val strRSS: String? = null,
    @SerializedName("strSport")
    val strSport: String? = null,
    @SerializedName("strStadium")
    val strStadium: String? = null,
    @SerializedName("strStadiumDescription")
    val strStadiumDescription: String? = null,
    @SerializedName("strStadiumLocation")
    val strStadiumLocation: String? = null,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String? = null,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null,
    @SerializedName("strTeamBanner")
    val strTeamBanner: String? = null,
    @SerializedName("strTeamFanart1")
    val strTeamFanart1: String? = null,
    @SerializedName("strTeamFanart2")
    val strTeamFanart2: String? = null,
    @SerializedName("strTeamFanart3")
    val strTeamFanart3: String? = null,
    @SerializedName("strTeamFanart4")
    val strTeamFanart4: String? = null,
    @SerializedName("strTeamJersey")
    val strTeamJersey: String? = null,
    @SerializedName("strTeamLogo")
    val strTeamLogo: String? = null,
    @SerializedName("strTeamShort")
    val strTeamShort: String? = null,
    @SerializedName("strTwitter")
    val strTwitter: String? = null,
    @SerializedName("strWebsite")
    val strWebsite: String? = null,
    @SerializedName("strYoutube")
    val strYoutube: String? = null
) : Parcelable
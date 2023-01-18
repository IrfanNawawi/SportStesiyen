package id.heycoding.sportstesiyen.data.remote.response


import com.google.gson.annotations.SerializedName

data class AllTeamsLeagueResponse(
    @SerializedName("teams")
    val teams: List<Team>
)

data class Team(
    @SerializedName("idAPIfootball")
    val idAPIfootball: String,
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("idLeague2")
    val idLeague2: String,
    @SerializedName("idLeague3")
    val idLeague3: String,
    @SerializedName("idLeague4")
    val idLeague4: String,
    @SerializedName("idLeague5")
    val idLeague5: String,
    @SerializedName("idLeague6")
    val idLeague6: String,
    @SerializedName("idLeague7")
    val idLeague7: Any,
    @SerializedName("idSoccerXML")
    val idSoccerXML: String,
    @SerializedName("idTeam")
    val idTeam: String,
    @SerializedName("intFormedYear")
    val intFormedYear: String,
    @SerializedName("intLoved")
    val intLoved: String,
    @SerializedName("intStadiumCapacity")
    val intStadiumCapacity: String,
    @SerializedName("strAlternate")
    val strAlternate: String,
    @SerializedName("strCountry")
    val strCountry: String,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: Any,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String,
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String,
    @SerializedName("strDescriptionHU")
    val strDescriptionHU: Any,
    @SerializedName("strDescriptionIL")
    val strDescriptionIL: Any,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: Any,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: Any,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: Any,
    @SerializedName("strDivision")
    val strDivision: Any,
    @SerializedName("strFacebook")
    val strFacebook: String,
    @SerializedName("strGender")
    val strGender: String,
    @SerializedName("strInstagram")
    val strInstagram: String,
    @SerializedName("strKeywords")
    val strKeywords: String,
    @SerializedName("strKitColour1")
    val strKitColour1: String,
    @SerializedName("strKitColour2")
    val strKitColour2: String,
    @SerializedName("strKitColour3")
    val strKitColour3: String,
    @SerializedName("strLeague")
    val strLeague: String,
    @SerializedName("strLeague2")
    val strLeague2: String,
    @SerializedName("strLeague3")
    val strLeague3: String,
    @SerializedName("strLeague4")
    val strLeague4: String,
    @SerializedName("strLeague5")
    val strLeague5: String,
    @SerializedName("strLeague6")
    val strLeague6: String,
    @SerializedName("strLeague7")
    val strLeague7: String,
    @SerializedName("strLocked")
    val strLocked: String,
    @SerializedName("strManager")
    val strManager: String,
    @SerializedName("strRSS")
    val strRSS: String,
    @SerializedName("strSport")
    val strSport: String,
    @SerializedName("strStadium")
    val strStadium: String,
    @SerializedName("strStadiumDescription")
    val strStadiumDescription: String,
    @SerializedName("strStadiumLocation")
    val strStadiumLocation: String,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb: String,
    @SerializedName("strTeam")
    val strTeam: String,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String,
    @SerializedName("strTeamBanner")
    val strTeamBanner: String,
    @SerializedName("strTeamFanart1")
    val strTeamFanart1: String,
    @SerializedName("strTeamFanart2")
    val strTeamFanart2: String,
    @SerializedName("strTeamFanart3")
    val strTeamFanart3: String,
    @SerializedName("strTeamFanart4")
    val strTeamFanart4: String,
    @SerializedName("strTeamJersey")
    val strTeamJersey: String,
    @SerializedName("strTeamLogo")
    val strTeamLogo: String,
    @SerializedName("strTeamShort")
    val strTeamShort: String,
    @SerializedName("strTwitter")
    val strTwitter: String,
    @SerializedName("strWebsite")
    val strWebsite: String,
    @SerializedName("strYoutube")
    val strYoutube: String
)
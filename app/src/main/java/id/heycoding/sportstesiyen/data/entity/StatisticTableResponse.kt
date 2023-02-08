package id.heycoding.sportstesiyen.data.entity


import com.google.gson.annotations.SerializedName

data class StatisticTableResponse(
    @SerializedName("table")
    val table: List<Table>
)

data class Table(
    @SerializedName("dateUpdated")
    val dateUpdated: String? = null,
    @SerializedName("idLeague")
    val idLeague: String? = null,
    @SerializedName("idStanding")
    val idStanding: String? = null,
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("intDraw")
    val intDraw: String? = null,
    @SerializedName("intGoalDifference")
    val intGoalDifference: String? = null,
    @SerializedName("intGoalsAgainst")
    val intGoalsAgainst: String? = null,
    @SerializedName("intGoalsFor")
    val intGoalsFor: String? = null,
    @SerializedName("intLoss")
    val intLoss: String? = null,
    @SerializedName("intPlayed")
    val intPlayed: String? = null,
    @SerializedName("intPoints")
    val intPoints: String? = null,
    @SerializedName("intRank")
    val intRank: String? = null,
    @SerializedName("intWin")
    val intWin: String? = null,
    @SerializedName("strDescription")
    val strDescription: String? = null,
    @SerializedName("strForm")
    val strForm: String? = null,
    @SerializedName("strLeague")
    val strLeague: String? = null,
    @SerializedName("strSeason")
    val strSeason: String? = null,
    @SerializedName("strTeam")
    val strTeam: String? = null,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null
)
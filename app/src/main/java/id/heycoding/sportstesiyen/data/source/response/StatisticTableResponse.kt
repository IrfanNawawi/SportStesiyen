package id.heycoding.sportstesiyen.data.source.response


import com.google.gson.annotations.SerializedName

data class StatisticTableResponse(
    @SerializedName("table")
    val table: List<Table>
)

data class Table(
    @SerializedName("dateUpdated")
    val dateUpdated: String,
    @SerializedName("idLeague")
    val idLeague: String,
    @SerializedName("idStanding")
    val idStanding: String,
    @SerializedName("idTeam")
    val idTeam: String,
    @SerializedName("intDraw")
    val intDraw: String,
    @SerializedName("intGoalDifference")
    val intGoalDifference: String,
    @SerializedName("intGoalsAgainst")
    val intGoalsAgainst: String,
    @SerializedName("intGoalsFor")
    val intGoalsFor: String,
    @SerializedName("intLoss")
    val intLoss: String,
    @SerializedName("intPlayed")
    val intPlayed: String,
    @SerializedName("intPoints")
    val intPoints: String,
    @SerializedName("intRank")
    val intRank: String,
    @SerializedName("intWin")
    val intWin: String,
    @SerializedName("strDescription")
    val strDescription: String,
    @SerializedName("strForm")
    val strForm: String,
    @SerializedName("strLeague")
    val strLeague: String,
    @SerializedName("strSeason")
    val strSeason: String,
    @SerializedName("strTeam")
    val strTeam: String,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String
)
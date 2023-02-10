package id.heycoding.sportstesiyen.domain.model

import com.google.gson.annotations.SerializedName

data class Statistic(
    val dateUpdated: String,
    val idLeague: String,
    val idStanding: String,
    val idTeam: String,
    val intDraw: String,
    val intGoalDifference: String,
    val intGoalsAgainst: String,
    val intGoalsFor: String,
    val intLoss: String,
    val intPlayed: String,
    val intPoints: String,
    val intRank: String,
    val intWin: String,
    val strDescription: String,
    val strForm: String,
    val strLeague: String,
    val strSeason: String,
    val strTeam: String,
    val strTeamBadge: String
)

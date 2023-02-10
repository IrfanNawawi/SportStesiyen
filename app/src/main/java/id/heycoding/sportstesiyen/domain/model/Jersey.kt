package id.heycoding.sportstesiyen.domain.model

import com.google.gson.annotations.SerializedName

data class Jersey(
    val date: String,
    val idEquipment: String,
    val idTeam: String,
    val strEquipment: String,
    val strSeason: String,
    val strType: String,
    val strUsername: String
)

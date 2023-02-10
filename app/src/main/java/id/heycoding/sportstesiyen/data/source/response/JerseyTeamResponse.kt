package id.heycoding.sportstesiyen.data.source.response


import com.google.gson.annotations.SerializedName

data class JerseyTeamResponse(
    @SerializedName("equipment")
    val equipment: List<Equipment>
)

data class Equipment(
    @SerializedName("date")
    val date: String,
    @SerializedName("idEquipment")
    val idEquipment: String,
    @SerializedName("idTeam")
    val idTeam: String,
    @SerializedName("strEquipment")
    val strEquipment: String,
    @SerializedName("strSeason")
    val strSeason: String,
    @SerializedName("strType")
    val strType: String,
    @SerializedName("strUsername")
    val strUsername: String
)
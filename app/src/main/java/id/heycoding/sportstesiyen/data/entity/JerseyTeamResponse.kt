package id.heycoding.sportstesiyen.data.entity


import com.google.gson.annotations.SerializedName

data class JerseyTeamResponse(
    @SerializedName("equipment")
    val equipment: List<Equipment>
)

data class Equipment(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("idEquipment")
    val idEquipment: String? = null,
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("strEquipment")
    val strEquipment: String? = null,
    @SerializedName("strSeason")
    val strSeason: String? = null,
    @SerializedName("strType")
    val strType: String? = null,
    @SerializedName("strUsername")
    val strUsername: String? = null
)
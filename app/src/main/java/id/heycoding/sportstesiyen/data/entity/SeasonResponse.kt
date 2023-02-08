package id.heycoding.sportstesiyen.data.entity


import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("seasons")
    val seasons: List<Season>
)

data class Season(
    @SerializedName("strSeason")
    val strSeason: String
)
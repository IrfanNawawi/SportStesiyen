package id.heycoding.sportstesiyen.data.source.response


import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("seasons")
    val seasons: List<Season>
)

data class Season(
    @SerializedName("strSeason")
    val strSeason: String
)
package id.heycoding.sportstesiyen.data.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllSportsResponse(

	@field:SerializedName("sports")
	val sports: List<SportsItem>
)

data class SportsItem(

	@field:SerializedName("idSport")
	val idSport: String? = null,

	@field:SerializedName("strFormat")
	val strFormat: String? = null,

	@field:SerializedName("strSport")
	val strSport: String? = null,

	@field:SerializedName("strSportIconGreen")
	val strSportIconGreen: String? = null,

	@field:SerializedName("strSportThumb")
	val strSportThumb: String? = null,

	@field:SerializedName("strSportDescription")
	val strSportDescription: String? = null
) : Serializable

package id.heycoding.sportstesiyen.domain.model

data class News(
    val publishedAt: String,
    val author: String,
    val urlToImage: String,
    val description: String,
    val source: Sources,
    val title: String,
    val url: String,
    val content: String
)

data class Sources(
    val name: String
)

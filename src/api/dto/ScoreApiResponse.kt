package api.dto

data class ScoreApiResponse(
    val status: String,
    val efficiency_score: Float,
    val accuracy_score1: Float,
    val accuracy_score2: Float,
    val score: Float,
)

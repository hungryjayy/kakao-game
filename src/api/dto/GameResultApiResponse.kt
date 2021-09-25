package api.dto

import api.vo.GameResult

data class GameResultApiResponse(
    val game_result: List<GameResult>
)

package api.dto

import api.vo.WaitingLine

data class WaitingLineApiResponse(
    val waiting_line: List<WaitingLine>,
)

package api.dto

import api.vo.Command

data class ChangeGradeApiRequest(
    val commands: List<Command>
)

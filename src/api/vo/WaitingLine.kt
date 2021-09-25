package api.vo

data class WaitingLine(
    val id: Int,
    val from: Int,
    var visited: Boolean = false,
)

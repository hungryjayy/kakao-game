package domain

import api.dto.UserInfoApiResponse
import api.dto.WaitingLineApiResponse

class Match {
    fun makeCurrentMatch(curTime: Int, waitingLine: WaitingLineApiResponse, userInfos: UserInfoApiResponse): List<List<Int>> {
        val matches = mutableListOf(listOf<Int>())
        for (myIndex in 0 until waitingLine.waiting_line.size) {
            if(waitingLine.waiting_line[myIndex].visited) continue
            val user = userInfos.user_info.find { it.id == waitingLine.waiting_line[myIndex].id }!!
            var minimum = INF
            var targetIndex = -1
            for (j in myIndex + 1 until waitingLine.waiting_line.size) { // find minimum diff user
                if(waitingLine.waiting_line[j].visited) continue

                val targetGrade = userInfos.user_info
                    .find { it.id == waitingLine.waiting_line[j].id }
                    ?.grade!!

                val diff = kotlin.math.abs(user.grade.let { targetGrade.minus(it) })
                if(diff < minimum && (isUnderThreshold(diff, userInfos) || isUrgent(curTime, myIndex, j, waitingLine))) {
                    minimum = diff
                    targetIndex = j
                }
            }
            if(targetIndex != -1) {
                val me = waitingLine.waiting_line[myIndex]
                val target = waitingLine.waiting_line[targetIndex]
                me.visited = true
                target.visited = true
                matches.add(listOf(me.id, target.id))
            }
        }
        return matches.toList()
    }

    private fun isUnderThreshold(diff: Int, userInfos: UserInfoApiResponse): Boolean =
        diff <= getCurrentMaxDiff(userInfos).toDouble() / DIV

    private fun getCurrentMaxDiff(userInfos: UserInfoApiResponse): Int =
        userInfos.user_info.maxOf { it.grade }.minus(userInfos.user_info.minOf { it.grade })

    private fun isUrgent(curTime: Int, myIndex: Int, targetIndex: Int, waitingLine: WaitingLineApiResponse): Boolean =
        waitingLine.waiting_line[myIndex].from == MATCH_UPPER_BOUND
                || waitingLine.waiting_line[targetIndex].from == MATCH_UPPER_BOUND

    companion object {
        const val INF = 987654321
        const val MATCH_UPPER_BOUND = 15
        const val DIV = 2.5
    }
}
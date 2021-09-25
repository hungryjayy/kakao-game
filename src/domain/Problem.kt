package domain

import api.RestApi
import api.dto.ChangeGradeApiRequest
import api.dto.MatchApiRequest
import api.dto.StartApiRequest

class Problem(
    private val match: Match,
    private val grade: Command,
    private val scenario: Int,
    private val authKey: String = RestApi.startApi(request = StartApiRequest(problem = scenario)).auth_key,
    private var result: String = ""
) {
    fun doProblem() {
        var curTime = 0
        do {
            curTime++
            val userInfoApiResponse = RestApi.userInfoApi(authKey)
            val waitingLineApiResponse = RestApi.waitingLineApi(authKey)
            val pairs = match.makeCurrentMatch(curTime, waitingLineApiResponse, userInfoApiResponse)
            val matchApiResponse = RestApi.matchApi(MatchApiRequest(pairs = pairs), authKey)
            result = matchApiResponse.status
            val gameResultApiResponse = RestApi.gameResultApi(authKey)

            val commands = grade.makeCommand(scenario, gameResultApiResponse, userInfoApiResponse)
            RestApi.changeGradeApi(ChangeGradeApiRequest(commands = commands), authKey)
        } while(result == SERVER_STATUS_READY)

        val score = RestApi.scoreApi(authKey)
        println(score)
    }

    companion object {
        const val SERVER_STATUS_READY = "ready"
    }
}
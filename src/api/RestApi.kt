package api

import api.dto.ChangeGradeApiRequest
import api.dto.ChangeGradeApiResponse
import api.dto.GameResultApiResponse
import api.dto.MatchApiRequest
import api.dto.MatchApiResponse
import api.dto.ScoreApiResponse
import api.dto.StartApiRequest
import api.dto.StartApiResponse
import api.dto.UserInfoApiResponse
import api.dto.WaitingLineApiResponse
import api.util.HttpUtil
import com.google.gson.Gson

// Reference: https://github.com/hungry-jay/kakao-t-simulation/blob/main/src/api/RestApi.kt
object RestApi {
    private const val BASE_URL = "https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod"
    private const val X_AUTH_TOKEN = "4e9c0aa396286567a1347ec792311436"
    private const val POST = "POST"
    private const val GET = "GET"
    private const val PUT = "PUT"

    fun startApi(request: StartApiRequest): StartApiResponse =
        HttpUtil.callApi(
            httpMethod = POST,
            urlString = "$BASE_URL/start",
            token = X_AUTH_TOKEN,
            body = Gson().toJson(request),
            doesInput = true,
            doesOutput = true,
        ).let { Gson().fromJson(it, StartApiResponse::class.java) }


    fun waitingLineApi(authKey: String): WaitingLineApiResponse =
        HttpUtil.callApi(
            httpMethod = GET,
            urlString = "$BASE_URL/waiting_line",
            authKey = authKey,
            doesInput = true,
            doesOutput = false,
        ).let { Gson().fromJson(it, WaitingLineApiResponse::class.java) }

    fun gameResultApi(authKey: String): GameResultApiResponse =
        HttpUtil.callApi(
            httpMethod = GET,
            urlString = "$BASE_URL/game_result",
            authKey = authKey,
            doesInput = true,
            doesOutput = false,
        ).let { Gson().fromJson(it, GameResultApiResponse::class.java) }

    fun userInfoApi(authKey: String): UserInfoApiResponse =
        HttpUtil.callApi(
            httpMethod = GET,
            urlString = "$BASE_URL/user_info",
            authKey = authKey,
            doesInput = true,
            doesOutput = false,
        ).let { Gson().fromJson(it, UserInfoApiResponse::class.java) }

    fun matchApi(request: MatchApiRequest, authKey: String): MatchApiResponse =
        HttpUtil.callApi(
            httpMethod = PUT,
            urlString = "$BASE_URL/match",
            authKey = authKey,
            body = Gson().toJson(request),
            doesInput = true,
            doesOutput = true,
        ).let { Gson().fromJson(it, MatchApiResponse::class.java) }

    fun changeGradeApi(request: ChangeGradeApiRequest, authKey: String): ChangeGradeApiResponse =
        HttpUtil.callApi(
            httpMethod = PUT,
            urlString = "$BASE_URL/change_grade",
            authKey = authKey,
            body = Gson().toJson(request),
            doesInput = true,
            doesOutput = true,
        ).let { Gson().fromJson(it, ChangeGradeApiResponse::class.java) }

    fun scoreApi(authKey: String): ScoreApiResponse =
        HttpUtil.callApi(
            httpMethod = GET,
            urlString = "$BASE_URL/score",
            authKey = authKey,
            doesInput = true,
            doesOutput = false,
        ).let { Gson().fromJson(it, ScoreApiResponse::class.java) }
}
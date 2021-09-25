package domain

import api.dto.GameResultApiResponse
import api.dto.UserInfoApiResponse
import api.vo.Command
import api.vo.GameResult

class Command {
    fun makeCommand(scenario: Int, gameResults: GameResultApiResponse, userInfos: UserInfoApiResponse): List<Command> {
        val commands = mutableListOf<Command>()

        /** this logic lower accuracy score1. **/
//        gameResults.game_result.map { result ->
//            userInfos.user_info.find { it.id == result.lose }
//                ?.apply {
//                    if(grade - result.taken >= 0) commands.add(Command(id, grade - result.taken))
//                }
//        }

        gameResults.game_result.map { result ->
            userInfos.user_info
                .filter { scenario == WITHOUT_ABUSE || !isAbusable(result, userInfos) }
                .find { it.id == result.win }
                ?.run { commands.add(Command(id, grade + result.taken)) }
        }
        return commands
    }

    private fun isAbusable(result: GameResult, userInfos: UserInfoApiResponse): Boolean =
        userInfos.user_info.find { it.id == result.lose }?.grade!! >= userInfos.user_info.find { it.id == result.win }?.grade!!
                && MIN_ABUSING_GAME_TIME <= result.taken
                && result.taken <= MAX_ABUSING_GAME_TIME


    companion object {
        const val MAX_ABUSING_GAME_TIME = 10
        const val MIN_ABUSING_GAME_TIME = 3
        const val WITHOUT_ABUSE = 1
    }
}
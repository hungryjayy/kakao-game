package api.dto

import api.vo.UserInfo

data class UserInfoApiResponse(
    val user_info: List<UserInfo>
)

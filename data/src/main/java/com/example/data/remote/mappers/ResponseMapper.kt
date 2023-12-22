package com.example.data.remote.mappers

import com.example.data.remote.model.UserInfo
import com.example.data.remote.model.UserInfoResponse

fun UserInfoResponse.toModel(): UserInfo {
    return UserInfo(
        code = this.code,
        status = this.status,
    )
}

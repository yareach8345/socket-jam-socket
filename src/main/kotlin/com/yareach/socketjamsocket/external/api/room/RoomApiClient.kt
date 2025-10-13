package com.yareach.socketjamsocket.external.api.room

import com.yareach.socketjamcommon.types.UserId
import com.yareach.socketjamsocket.external.api.room.dto.UserJoinInfoDto

interface RoomApiClient {
    fun getUserJoinInfo(userId: UserId): UserJoinInfoDto
}
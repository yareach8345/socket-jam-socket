package com.yareach.socketjamsocket.external.api.room

import com.yareach.socketjamcommon.service.enum.ServiceHost
import com.yareach.socketjamcommon.types.UserId
import com.yareach.socketjamsocket.external.api.room.dto.UserJoinInfoDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate

@Service
class RoomApiRestTemplateClient(
    private val restTemplate: RestTemplate,
): RoomApiClient {

    override fun getUserJoinInfo(userId: UserId): UserJoinInfoDto {
        val path = "/internal/v1/users/$userId/joinInfo"
        val uri = ServiceHost.USER.withPath(path)

        val result = restTemplate.getForObject(uri, UserJoinInfoDto::class.java)
            ?: throw HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to get user info. Response is null",
            )

        return result
    }
}
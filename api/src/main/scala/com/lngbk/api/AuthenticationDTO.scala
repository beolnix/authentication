package com.lngbk.api

import java.util.UUID

import com.lngbk.commons.api.dto.{LngbkRequest, LngbkResponse}

case class VerifyResponse(
                           userUuid: UUID,
                           login: String,
                           roles: Set[String],
                           errorCode: Option[String]
                         ) extends LngbkResponse(errorCode)

case class VerifyRequest(
                          accessToken: String,
                          requestUuid: String
                        ) extends LngbkRequest(requestUuid)

case class LoginResponse(
                          accessToken: String,
                          refreshToken: String,
                          period: Long,
                          errorCode: Option[String]
                        ) extends LngbkResponse(errorCode)

case class LoginRequest(
                         login: String,
                         password: String,
                         requestUuid: String
                       ) extends LngbkRequest(requestUuid)
package com.lngbk.api

import java.util.UUID

import com.lngbk.commons.api.dto.{LngbkRequest, LngbkResponse}

case class VerifyResponse(
                           userUuid: UUID,
                           login: String,
                           roles: Set[String]
                         )

case class VerifyRequest(
                          accessToken: String
                        )

case class LoginResponse(
                          accessToken: String,
                          refreshToken: String,
                          period: Long
                        )

case class LoginRequest(
                         login: String,
                         password: String
                       )
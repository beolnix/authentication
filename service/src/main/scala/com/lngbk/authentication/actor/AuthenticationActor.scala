package com.lngbk.authentication.actor

import java.util.UUID

import com.lngbk.api._
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}
import com.lngbk.commons.api.server.LngbkActor
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by beolnix on 10/09/16.
  */
class AuthenticationActor extends LngbkActor {
  private val logger: Logger = LoggerFactory.getLogger(classOf[AuthenticationActor])

  override def process: Receive = {
    case LoginRequest(login, password, requestUuid) => {
      logger.info(s"Got login request: $login, password, $requestUuid")
      sender() ! LoginResponse("test", "test", 123, None)
    }
    case SignUpRequest(email, password, requestUuid) => {
      logger.info(s"got sign up request: $email, password, $requestUuid")
      sender() ! SignUpResponse(UUID.randomUUID().toString, None)
    }
    case VerifyRequest(accessToken, requestUuid) => {
      logger.info(s"got verify request: $accessToken, $requestUuid")
      sender() ! VerifyResponse(UUID.randomUUID().toString, "test", Set(), None)
    }
    case RefreshTokenRequest(refreshToken, requestUuid) => {
      logger.info(s"got refresh token request: $refreshToken, $requestUuid")
      sender() ! RefreshTokenResponse("test", None)
    }
    case other => {
      println(s"SERVER GOT $other")
      throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }
}

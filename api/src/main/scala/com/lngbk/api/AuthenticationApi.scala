package com.lngbk.api

import akka.actor.Props
import com.lngbk.commons.api.client.LngbkApi
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}
import com.lngbk.commons.api.server.LngbkActor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class AuthenticationActor extends LngbkActor

object AuthenticationActor {
  val serviceName = "authentication"
}

object AuthenticationApi extends LngbkApi(AuthenticationActor.serviceName, Props[AuthenticationActor]) {

  def login(request: LoginRequest): Future[LoginResponse] = {
    val response: Future[Any] = router ? request
    response.map {
      case LoginResponse(accessToken, refreshToken, period, errorCode) => LoginResponse(accessToken, refreshToken, period, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

  def verify(request: VerifyRequest): Future[VerifyResponse] = {
    val response = router ? request
    response.map {
      case VerifyResponse(userUuid, login, roles, errorCode) => VerifyResponse(userUuid, login, roles, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

}
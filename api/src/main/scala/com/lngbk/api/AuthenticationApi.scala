package com.lngbk.api

import akka.actor.Props
import com.lngbk.commons.api.client.LngbkApi
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}
import com.lngbk.commons.api.server.LngbkActor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AuthenticationConstants {
  val serviceName = "authentication"
}

object AuthenticationApi extends LngbkApi(AuthenticationConstants.serviceName) {

  initApi()

  def login(request: LoginRequest): Future[LoginResponse] = {
    val response: Future[Any] = router ? request
    response.map {
      case LoginResponse(accessToken, refreshToken, period) => LoginResponse(accessToken, refreshToken, period)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

  def verify(request: VerifyRequest): Future[VerifyResponse] = {
    val response = router ? request
    response.map {
      case VerifyResponse(userUuid, login, roles) => VerifyResponse(userUuid, login, roles)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

}

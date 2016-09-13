package com.lngbk.api

import akka.actor.{ActorPath}
import com.lngbk.commons.api.client.LngbkApi
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AuthenticationConstants {
  val serviceName = "authentication"
}

object AuthenticationApi {
  private var api: AuthenticationApi = _

  def apply(actorPath: Option[ActorPath] = None): Unit = {
    api = new AuthenticationApi(actorPath)
  }

  def login(request: LoginRequest): Future[LoginResponse] = {
    val response: Future[Any] = api.router ? request
    response.map {
      case LoginResponse(accessToken, refreshToken, period, errorCode) => LoginResponse(accessToken, refreshToken, period, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

  def verify(request: VerifyRequest): Future[VerifyResponse] = {
    val response = api.router ? request
    response.map {
      case VerifyResponse(userUuid, login, roles, errorCode) => VerifyResponse(userUuid, login, roles, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

  def signUp(request: SignUpRequest): Future[SignUpResponse] = {
    val response = api.router ? request
    response.map {
      case SignUpResponse(userUuid, errorCode) => SignUpResponse(userUuid, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

  def refreshToken(request: RefreshTokenRequest): Future[RefreshTokenResponse] = {
    val response = api.router ? request
    response.map {
      case RefreshTokenResponse(accessToken, errorCode) => RefreshTokenResponse(accessToken, errorCode)
      case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }

}

class AuthenticationApi(actorPath: Option[ActorPath] = None) extends LngbkApi(AuthenticationConstants.serviceName, actorPath = actorPath) {

  initApi()

}

package com.lngbk.authentication.actor

import com.lngbk.api.AuthenticationActor
import com.lngbk.api.{LoginRequest, LoginResponse}
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}
import com.lngbk.commons.api.server.LngbkActor

/**
  * Created by beolnix on 10/09/16.
  */
class AuthenticationActorImpl extends AuthenticationActor {
  override def process: Receive = {
    case LoginRequest(login, password, requestUuid) => {
      sender() ! LoginResponse("test", "test", 123, Option.empty)
    }
    case _ => throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
  }
}

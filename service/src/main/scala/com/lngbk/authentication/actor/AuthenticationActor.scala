package com.lngbk.authentication.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.lngbk.api.{LoginRequest, LoginResponse}
import com.lngbk.commons.api.dto.{LngbkVersionRequest, LngbkVersionResponse}
import com.lngbk.commons.api.errors.{ApiCriticalError, CommonErrorCodes}
import com.lngbk.commons.api.server.LngbkActor
import com.lngbk.commons.api.util.VersionHelper
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by beolnix on 10/09/16.
  */
class AuthenticationActor extends LngbkActor {
  private val logger: Logger = LoggerFactory.getLogger(classOf[AuthenticationActor])

  override def process: Receive = {
    case LoginRequest(login, password, requestUuid) => {
      sender() ! LoginResponse("test", "test", 123, None)
    }
    case other => {
      println(s"SERVER GOT $other")
      throw new ApiCriticalError(CommonErrorCodes.PIZDEC)
    }
  }
}

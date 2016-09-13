package com.lngbk.authentication


import akka.actor.Props
import com.lngbk.api.{AuthenticationApi, AuthenticationConstants}
import com.lngbk.authentication.actor.AuthenticationActor
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.ServiceBootstrapDirector
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by beolnix on 10/09/16.
  */
object AuthenticationService extends App {
  private val logger: Logger = LoggerFactory.getLogger("AuthenticationService")

  val api = new AuthenticationApi()
  ServiceBootstrapDirector.initService(false, true)
  SystemManager.system.actorOf(Props[AuthenticationActor], AuthenticationConstants.serviceName)
  logger.info("Service started successfully")
}

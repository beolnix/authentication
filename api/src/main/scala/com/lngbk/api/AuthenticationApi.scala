package com.lngbk.api

import akka.actor.Props
import com.lngbk.authentication.AuthenticationActor
import com.lngbk.commons.discovery.LngbkRouter
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.{DependenciesManager, ServiceBootstrapDirector}

/**
  * Created by beolnix on 09/09/16.
  */
object AuthenticationApi {

  private val SERVICE_NAME = "authentication-service"
  private val _router: LngbkRouter = LngbkRouter(
    "authentication-service",
    SystemManager.system,
    Props[AuthenticationActor]
  )

  DependenciesManager.checkIn(_router)

}

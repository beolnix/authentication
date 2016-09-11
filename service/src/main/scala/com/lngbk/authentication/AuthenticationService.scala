package com.lngbk.authentication


import akka.actor.Props
import com.lngbk.api.{AuthenticationActor, AuthenticationApi}
import com.lngbk.authentication.actor.AuthenticationActorImpl
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.ServiceBootstrapDirector

/**
  * Created by beolnix on 10/09/16.
  */
object AuthenticationService extends App {
  AuthenticationApi
  ServiceBootstrapDirector.initService(false, true)
  SystemManager.system.actorOf(Props[AuthenticationActorImpl], AuthenticationActor.serviceName)
}

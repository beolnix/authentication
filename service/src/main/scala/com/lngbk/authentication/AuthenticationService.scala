package com.lngbk.authentication


import akka.actor.Props
import com.lngbk.authentication.actor.AuthenticationActorImpl
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.service.LngbkService

/**
  * Created by beolnix on 10/09/16.
  */
object AuthenticationService extends LngbkService(false, true) with App {
  SystemManager.system.actorOf(Props[AuthenticationActorImpl])
}

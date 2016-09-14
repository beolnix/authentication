package com.lngbk.authentication


import com.lngbk.api.{AuthenticationApi, AuthenticationConstants}
import com.lngbk.authentication.actor.AuthenticationActor
import com.lngbk.commons.service.LngbkService


object AuthenticationService
  extends LngbkService[AuthenticationActor](false, true, AuthenticationConstants.serviceName)
  with App {

  override def withDependencies(): Unit = {
    AuthenticationApi()
  }
}

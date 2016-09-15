package com.lngbk.authentication.domain

case class User(id: String, email: String, passwordHash: String, roles: Set[String], active: Boolean)

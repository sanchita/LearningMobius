package me.saket.mobius.login.http

import io.reactivex.Single

interface LoginApi {
  fun login(loginRequest: LoginRequest): Single<LoginResponse>
}

package me.saket.mobius.login.effecthandlers

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import me.saket.mobius.login.AttemptLogin
import me.saket.mobius.login.LoginEffect
import me.saket.mobius.login.LoginEvent
import me.saket.mobius.login.LoginFailed
import me.saket.mobius.login.LoginSucceeded
import me.saket.mobius.login.SaveToken
import me.saket.mobius.login.http.LoginApi
import me.saket.mobius.login.http.LoginRequest

object LoginEffectHandlers {
  fun create(
      loginApi: LoginApi,
      userRepository: UserRepository,
      screen: LoginScreen
  ): ObservableTransformer<LoginEffect, LoginEvent> {
    return RxMobius
        .subtypeEffectHandler<LoginEffect, LoginEvent>()
        .addTransformer(AttemptLogin::class.java, attemptLoginTransformer(loginApi))
        .addConsumer(SaveToken::class.java) { saveTokenEffect -> userRepository.saveToken(saveTokenEffect.token) }
        .build()
  }

  private fun attemptLoginTransformer(
      loginApi: LoginApi
  ): ObservableTransformer<AttemptLogin, LoginEvent> {
    return ObservableTransformer { attemptLoginEffects ->
      attemptLoginEffects.flatMapSingle {
        loginApi
            .login(LoginRequest(it.email, it.password))
            .map { response -> LoginSucceeded(response.token) as LoginEvent }
            .onErrorReturn { LoginFailed }
      }
    }
  }
}

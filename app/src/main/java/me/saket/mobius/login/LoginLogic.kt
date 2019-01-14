package me.saket.mobius.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next

object LoginLogic {
  fun update(
      model: LoginModel,
      event: LoginEvent
  ): Next<LoginModel, LoginEffect> {
    return when (event) {
      is EmailChanged -> next(model.withEmail(event.email))
      is PasswordChanged -> next(model.withPassword(event.password))
      is LoginAttempted -> next(model.attemptLogin(), setOf(AttemptLogin(event.email, event.password)))
      is LoginSucceeded -> next(model.loginSucceeded(), setOf(SaveToken(event.token), GoToHome))
      is LoginFailed -> next(model.loginFailed(), setOf(ShowLoginFailureNotification))
      else -> TODO()
    }
  }
}

package me.saket.mobius.login

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

data class LoginModel(
    val email: Option<String>,
    val password: Option<String>
) {

  companion object {
    val BLANK = LoginModel(email = None, password = None)
  }

  val isReadyForLogin: Boolean
    get() = email is Some && password is Some

  fun withEmail(email: String): LoginModel =
      copy(email = if (email.isBlank()) None else Some(email))

  fun withPassword(password: String): LoginModel =
      copy(password = if (password.isBlank()) None else Some(password))
}

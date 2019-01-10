package me.saket.mobius.login

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

data class LoginModel(val email: Option<String>) {
  companion object {
    val BLANK = LoginModel(None)
  }

  fun withEmail(email: String): LoginModel =
      copy(email = Some(email))
}

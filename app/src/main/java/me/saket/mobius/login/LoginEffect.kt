package me.saket.mobius.login

sealed class LoginEffect

data class AttemptLogin(val email: String, val password: String) : LoginEffect()

data class SaveToken(val token: String) : LoginEffect()

object GoToHome : LoginEffect()

object ShowLoginFailureNotification : LoginEffect()

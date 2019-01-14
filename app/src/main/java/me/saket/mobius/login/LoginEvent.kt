package me.saket.mobius.login

sealed class LoginEvent

data class EmailChanged(val email: String) : LoginEvent()

data class PasswordChanged(val password: String) : LoginEvent()

data class LoginAttempted(val email: String, val password: String) : LoginEvent()

data class LoginSucceeded(val token: String) : LoginEvent()

object LoginFailed : LoginEvent()

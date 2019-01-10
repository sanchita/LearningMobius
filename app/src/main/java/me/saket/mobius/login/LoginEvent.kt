package me.saket.mobius.login

sealed class LoginEvent

data class EmailChanged(val email: String) : LoginEvent()

package me.saket.mobius.login

sealed class LoginEffect

object AttemptLoginNetworkEffect : LoginEffect()

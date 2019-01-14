package me.saket.mobius.login.effecthandlers

interface UserRepository {
  fun saveToken(token: String)
}

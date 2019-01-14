package me.saket.mobius.login

import com.spotify.mobius.test.NextMatchers.hasEffects
import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  private val updateSpec: UpdateSpec<LoginModel, LoginEvent, LoginEffect> = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)

  @Test
  fun `when email is entered, then update state with email address`() {
    val email = "saket@saket.me"
    val blankModel = LoginModel.BLANK

    updateSpec
        .given(blankModel)
        .`when`(EmailChanged(email))
        .then(
            assertThatNext(
                hasModel(blankModel.withEmail(email)),
                hasNoEffects()
            )
        )
  }

  @Test
  fun `when password is entered, then update state with password`() {
    val password = "password"
    val blankModel = LoginModel.BLANK

    updateSpec
        .given(blankModel)
        .`when`(PasswordChanged(password))
        .then(
            assertThatNext(
                hasModel(blankModel.withPassword(password)),
                hasNoEffects()
            )
        )
  }

  @Test
  fun `when login is attempted, then make a login network call`() {
    val email = "saket@saket.me"
    val password = "password"
    val validModel = LoginModel.BLANK
        .withEmail(email)
        .withPassword(password)

    updateSpec
        .given(validModel)
        .`when`(LoginAttempted(email, password))
        .then(
            assertThatNext(
                hasModel(validModel.attemptLogin()),
                hasEffects(AttemptLogin(email, password) as LoginEffect)
            )
        )
  }

  @Test
  fun `when attempted login succeeds, then save the token and go to home screen`() {
    val attemptingLoginModel = LoginModel.BLANK
        .withEmail("saket@saket.me")
        .withPassword("password")
        .attemptLogin()

    val token = "token"

    updateSpec
        .given(attemptingLoginModel)
        .`when`(LoginSucceeded(token))
        .then(
            assertThatNext(
                hasModel(attemptingLoginModel.loginSucceeded()),
                hasEffects(SaveToken(token) as LoginEffect, GoToHome)
            )
        )
  }

  @Test
  fun `when attempted login fails, then show a notification`() {
    val attemptingLoginModel = LoginModel.BLANK
        .withEmail("saket@saket.me")
        .withPassword("password")
        .attemptLogin()

    updateSpec
        .given(attemptingLoginModel)
        .`when`(LoginFailed)
        .then(
            assertThatNext(
                hasModel(attemptingLoginModel.loginFailed()),
                hasEffects(ShowLoginFailureNotification as LoginEffect)
            )
        )
  }
}

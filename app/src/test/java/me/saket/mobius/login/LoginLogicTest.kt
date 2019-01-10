package me.saket.mobius.login

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)

  @Test
  fun `when email is entered, then update state with email address`() {
    val email = "saket@saket.me"

    updateSpec
        .given(LoginModel.BLANK)
        .`when`(EmailChanged(email))
        .then(
            assertThatNext(
                hasModel(LoginModel.BLANK.withEmail(email)),
                hasNoEffects()
            )
        )
  }

  @Test
  fun `when password is entered, then update state with password`() {
    val password = "password"

    updateSpec
        .given(LoginModel.BLANK)
        .`when`(PasswordChanged(password))
        .then(
            assertThatNext(
                hasModel(LoginModel.BLANK.withPassword(password)),
                hasNoEffects()
            )
        )
  }
}

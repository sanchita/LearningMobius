package me.saket.mobius.login

import arrow.core.Some
import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  @Test
  fun `when email is entered, then update state with email address`() {
    val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)
    val email = "saket@saket.me"

    updateSpec
        .given(LoginModel.BLANK)
        .`when`(EmailChanged(email))
        .then(
            assertThatNext(
                hasModel(LoginModel(Some(email))),
                hasNoEffects()
            )
        )
  }
}

package me.saket.mobius.login

import arrow.core.None
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginModelTest {

  @Test
  fun `when both email and password are non-empty, then model is ready for login`() {
    val nonEmptyModel = LoginModel.BLANK.withEmail("saket@saket.me").withPassword("password")

    assertEquals(true, nonEmptyModel.isReadyForLogin)
  }

  @Test
  fun `when email is empty, then mark it as none`() {
    val emptyEmailModel = LoginModel.BLANK.withEmail("something").withEmail("")

    assertEquals(None, emptyEmailModel.email)
  }

  @Test
  fun `when email is empty, then model is not ready for login`() {
    val emptyEmailModel = LoginModel.BLANK.withEmail("").withPassword("password")

    assertEquals(false, emptyEmailModel.isReadyForLogin)
  }

  @Test
  fun `when password is empty, then mark it as none`() {
    val emptyPasswordModel = LoginModel.BLANK.withPassword("something").withPassword("")

    assertEquals(None, emptyPasswordModel.password)
  }

  @Test
  fun `when password is empty, then model is not ready for login`() {
    val emptyPasswordModel = LoginModel.BLANK.withEmail("something").withPassword("")

    assertEquals(false, emptyPasswordModel.isReadyForLogin)
  }
}

package me.saket.mobius.login.effecthandlers

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.saket.mobius.login.AttemptLogin
import me.saket.mobius.login.GoToHome
import me.saket.mobius.login.LoginFailed
import me.saket.mobius.login.LoginSucceeded
import me.saket.mobius.login.SaveToken
import me.saket.mobius.login.http.LoginApi
import me.saket.mobius.login.http.LoginRequest
import me.saket.mobius.login.http.LoginResponse
import me.saket.mobius.login.test.EffectHandlerTestCase
import org.junit.Test

class LoginEffectHandlersTest {
  private val loginApi = mock<LoginApi>()
  private val userRepository = mock<UserRepository>()
  private val screen = mock<LoginScreen>()
  private val loginEffectHandlerTestCase = EffectHandlerTestCase(LoginEffectHandlers.create(loginApi, userRepository, screen))

  @Test
  fun `when attempt login succeeds, then it dispatches login succeeded event`() {
    // given
    val token = "token"
    val email = "someone@somewhere.in"
    val password = "secret"
    whenever(loginApi.login(LoginRequest(email, password)))
        .thenReturn(Single.just(LoginResponse(token)))

    // when
    loginEffectHandlerTestCase
        .dispatchEffect(AttemptLogin(email, password))

    // then
    loginEffectHandlerTestCase
        .assertEvents(LoginSucceeded(token))
  }

  @Test
  fun `when attempt login fails, then it dispatches login failed event`() {
    // given
    val email = "someone@somewhere.in"
    val password = "secret"
    whenever(loginApi.login(LoginRequest(email, password)))
        .thenReturn(Single.error(RuntimeException("Network error")))

    // when
    loginEffectHandlerTestCase
        .dispatchEffect(AttemptLogin(email, password))

    //then
    loginEffectHandlerTestCase
        .assertEvents(LoginFailed)
  }

  @Test
  fun `when save token, then store token in repository`() {
    // when
    val token = "some-token"
    loginEffectHandlerTestCase
        .dispatchEffect(SaveToken(token))

    // then
    verify(userRepository).saveToken(token)
    verifyNoMoreInteractions(userRepository)
  }

  @Test
  fun `when go to home, then take to home screen`(){
    //when
    loginEffectHandlerTestCase
        .dispatchEffect(GoToHome)

    //then
    verify(screen).goToHome()
  }
}

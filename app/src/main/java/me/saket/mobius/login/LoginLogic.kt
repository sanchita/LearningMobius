package me.saket.mobius.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next

object LoginLogic {
  fun update(
      model: LoginModel,
      event: LoginEvent
  ): Next<LoginModel, LoginEffect> {
    return when (event) {
      is EmailChanged -> next(model.withEmail(event.email))
    }
  }
}

package me.saket.mobius.login.test

import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

class EffectHandlerTestCase<F, E>(
    effectHandler: ObservableTransformer<F, E>
) {
  private val incomingEffectsSubject = PublishSubject.create<F>()
  private val outgoingEventsObserver = incomingEffectsSubject.compose(effectHandler).test()

  fun dispatchEffect(effect: F) {
    incomingEffectsSubject.onNext(effect)
  }

  fun assertEvents(vararg events: E) {
    outgoingEventsObserver.assertValues(*events)
  }
}

package com.tomcz.mvi

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface representing simplest possible Unidirectional Data Flow.
 * Exposes [state] that can be observed and allows to send events through [sendEvent] method.
 * Those two actions create a unidirectional loop: Observe the state and render the UI,
 * send events from the UI and listen for new state updates based on those events.
 *
 * @param EV represents *events* from UI, e.g. button click.
 * @param ST represents UI's *state*, e.g. `isLoading: Boolean`
 */
interface StateProcessor<in EV : Any, out ST : Any> {

    /**
     * [StateFlow] of [state][ST] that represents the UI. Everything that's suppose
     * to be rendered based on [processor's][StateProcessor] data
     * should be included in [state][ST] object.
     *
     * Example of [state][ST] class:
     * ```kotlin
     * data class MyState(
     *  val isLoading: Boolean = false
     * )
     * ```
     */
    val state: StateFlow<ST>

    /**
     * Method that takes the events that the [processor][StateProcessor] uses to modify the [state].
     *
     * Example of an [event][EV] class:
     * ```kotlin
     * sealed interface MyEvent {
     *  object ButtonClicked: MyEvent
     * }
     * ```
     *
     * @param event object of an [EV] class that represents UI's event, e.g. button click.
     */
    fun sendEvent(event: EV)
}

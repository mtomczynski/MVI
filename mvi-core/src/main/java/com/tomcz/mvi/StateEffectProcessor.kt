package com.tomcz.mvi

import kotlinx.coroutines.flow.Flow

/**
 * Extension of [StateProcessor] that adds [effects][effect].
 *
 * They represent single events produced by the [processor][StateEffectProcessor], that aren't
 * cached or replayed on any occasion.
 *
 * They're useful for representing for example navigation events that are handled by the UI,
 * e.g. `startActivity(context, ...)`.
 *
 * Effects are not replayed on configuration change, like the [state] is.
 *
 * Real life example: show the popup on the button click. If we'd store the popup shown information
 * in the [state], then when the user would rotate the screen, popup would be shown again,
 * which would be a bug.
 */
interface StateEffectProcessor<in EV : Any, out ST : Any, out EF : Any> : StateProcessor<EV, ST> {

    /**
     * [Flow] of [effects][EF].
     * They represent single events produced by the [processor][StateEffectProcessor]
     * and consumed on the UI, that aren't cached or replayed on any occasion.
     *
     * Example:
     * ```kotlin
     * sealed interface MyEffect {
     *  object ShowPopup: MyEffect
     * }
     *
     * val processor: StateEffectProcessor<...> = stateEffectProcessor() { effects, event ->
     *  when (event) {
     *      ButtonClick -> effects.send(ShowPopup)
     *      ...
     *  }
     *  ...
     * }
     * ```
     */
    val effect: Flow<EF>
}

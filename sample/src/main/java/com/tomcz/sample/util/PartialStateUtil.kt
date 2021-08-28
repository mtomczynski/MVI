package com.tomcz.sample.util

import com.tomcz.mvi.PartialState
import com.tomcz.mvi.common.NoAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Extension function to conveniently return [Flow] of
 * [NoAction] [PartialState] for any processor. It's not in the library itself,
 * because not everyone would like to have an extension function on Any type.
 *
 * @receiver [Any] type, to be able to chain to [com.tomcz.mvi.EffectsCollector.send] method
 * like so:
 * ```kotlin
 * effects.send(MyEffect).thenNoAction()`
 * ```
 * @return [Flow] of [PartialState] that represents [NoAction]
 */
fun <T : Any> Any.thenNoAction(): Flow<PartialState<T>> = flowOf(NoAction())

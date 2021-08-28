package com.tomcz.mvi.common

import com.tomcz.mvi.PartialState

/**
 * Factory method to create implementation of [PartialState] with
 * with specified [type][T] that represents no state change on the processor.
 *
 * @return new object of [PartialState] that doesn't modify the
 * state on [PartialState.reduce] method
 */
fun <T : Any> NoAction(): PartialState<T> = object : PartialState<T> {
    override fun reduce(oldState: T): T = oldState
}

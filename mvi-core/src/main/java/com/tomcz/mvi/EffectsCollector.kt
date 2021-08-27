package com.tomcz.mvi

import kotlinx.coroutines.flow.Flow

interface EffectsCollector<EF : Any, ST : Any> {
    fun send(effect: EF): Flow<PartialState.NoAction<ST>>
}

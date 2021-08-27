package com.tomcz.mvi.internal

import com.tomcz.mvi.EffectProcessor
import com.tomcz.mvi.EffectsCollector
import com.tomcz.mvi.PartialState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

internal class FlowEffectProcessor<in EV : Any, EF : Any>(
    private val scope: CoroutineScope,
    prepare: (suspend (EffectsCollector<EF, Nothing>) -> Unit)? = null,
    private val mapper: (suspend (EffectsCollector<EF, Nothing>, EV) -> Unit)? = null,
) : EffectProcessor<EV, EF> {

    override val effect: Flow<EF>
        get() = effectSharedFlow
    private val effectSharedFlow: MutableSharedFlow<EF> = MutableSharedFlow(replay = 0)

    private val effectsCollector: EffectsCollector<EF, Nothing> =
        object : EffectsCollector<EF, Nothing> {
            override fun send(effect: EF): Flow<PartialState<Nothing>> {
                scope.launch { effectSharedFlow.emit(effect) }
                return emptyFlow()
            }
        }

    init {
        prepare?.let {
            scope.launch { it(effectsCollector) }
        }
    }

    override fun sendEvent(event: EV) {
        mapper?.let {
            scope.launch { it(effectsCollector, event) }
        }
    }
}

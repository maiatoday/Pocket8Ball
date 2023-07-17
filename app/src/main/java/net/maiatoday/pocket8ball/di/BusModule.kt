package net.maiatoday.pocket8ball.di

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface FlowBus {
    val bus: SharedFlow<BusEvent>
    suspend fun post(event: BusEvent)
}

object BusModule: FlowBus {
    private val _bus = MutableSharedFlow<BusEvent>(replay = 0)
    override val bus: SharedFlow<BusEvent>
        get() = _bus

    override suspend fun post(event: BusEvent) {
        _bus.emit(event)
    }
}

sealed class BusEvent

data class MessageFromTheAether(val answer: String) : BusEvent()

object ShakeItUp : BusEvent()
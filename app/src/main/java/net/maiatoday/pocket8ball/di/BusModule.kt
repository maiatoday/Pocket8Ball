package net.maiatoday.pocket8ball.di

import com.squareup.otto.Bus

object BusModule {
    val bus = Bus()
}

data class MessageFromeTheAether(val answer: String)

object ShakeItUp
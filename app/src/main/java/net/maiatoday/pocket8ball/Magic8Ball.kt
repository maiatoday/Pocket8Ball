package net.maiatoday.pocket8ball

import com.squareup.otto.Subscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.maiatoday.pocket8ball.di.BusModule
import net.maiatoday.pocket8ball.di.MessageFromeTheAether
import net.maiatoday.pocket8ball.di.ShakeItUp

interface Magic8Ball {
    suspend fun shake()
    fun release()
}

class RealMagic8Ball : Magic8Ball {
    init {
        BusModule.bus.register(this)
    }

    private val answers: List<String> = listOf(
        "It is certain.",
        "It is decidedly so.",
        "Without a doubt.",
        "Yes - definitely.",
        "You may rely on it.",
        "As I see it, yes.",
        "Most likely.",
        "Outlook good.",
        "Yes.",
        "Signs point to yes.",
        "Reply hazy, try again.",
        "Ask again later.",
        "Better not tell you now.",
        "Cannot predict now.",
        "Concentrate and ask again.",
        "Don't count on it.",
        "My reply is no.",
        "My sources say no.",
        "Outlook not so good.",
        "Very doubtful."
    )

    @Subscribe
    fun shakeItUp(event: ShakeItUp) {
        CoroutineScope(Dispatchers.Main).launch {
            shake()
        }
    }

    override suspend fun shake() {
        val randomMillis = (500 + 1000 * Math.random()).toLong()
        delay(randomMillis)
        latest = answers.shuffled().first()
        BusModule.bus.post(MessageFromeTheAether(latest))
    }

    private var latest: String = ""

    override fun release() {
        BusModule.bus.unregister(this)
    }

}
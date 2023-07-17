package net.maiatoday.pocket8ball

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import net.maiatoday.pocket8ball.di.BusModule
import net.maiatoday.pocket8ball.di.MessageFromTheAether
import net.maiatoday.pocket8ball.di.ShakeItUp

interface Magic8Ball {
    suspend fun shake()
}

class RealMagic8Ball : Magic8Ball {
    init {
        CoroutineScope(Dispatchers.Main).launch {
            BusModule.bus.filter { event -> event is ShakeItUp }
                .collectLatest { shake() }
        }
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

    override suspend fun shake() {
        val randomMillis = (500 + 1000 * Math.random()).toLong()
        delay(randomMillis)
        latest = answers.shuffled().first()
        BusModule.post(MessageFromTheAether(latest))
    }

    private var latest: String = ""

}
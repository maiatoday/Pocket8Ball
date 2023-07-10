package net.maiatoday.pocket8ball

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface Magic8Ball {
    suspend fun shake()
    val answer: StateFlow<String>
}

class RealMagic8Ball : Magic8Ball {

    private val _answer = MutableStateFlow("")
    override val answer: StateFlow<String>
        get() = _answer.asStateFlow()

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
        _answer.value = answers.shuffled().first()
    }

}
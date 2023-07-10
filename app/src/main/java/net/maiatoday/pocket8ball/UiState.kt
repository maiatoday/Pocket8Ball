package net.maiatoday.pocket8ball

sealed class UiState

object Loading: UiState()

object PleaseAsk: UiState()

class Reveal(val answer:String): UiState()

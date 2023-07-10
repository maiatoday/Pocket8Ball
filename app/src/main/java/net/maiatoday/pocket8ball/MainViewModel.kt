package net.maiatoday.pocket8ball

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val eightBall: Magic8Ball = RealMagic8Ball()) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(PleaseAsk)
    val uiState = _uiState

    init {
        viewModelScope.launch {
            eightBall.answer.collect { answer ->
                if (answer.isEmpty()) {
                    _uiState.value = PleaseAsk
                } else {
                    _uiState.value = Reveal(answer)
                    delay(5_000)
                    _uiState.value = PleaseAsk
                }
            }
        }
    }

    fun shake() {
        if (uiState.value is PleaseAsk) {
            viewModelScope.launch {
                _uiState.value = Loading
                eightBall.shake()
            }
        }
    }

}
package net.maiatoday.pocket8ball

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.otto.Subscribe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.maiatoday.pocket8ball.di.BusModule
import net.maiatoday.pocket8ball.di.MessageFromeTheAether
import net.maiatoday.pocket8ball.di.ShakeItUp

class MainViewModel(private val eightBall: Magic8Ball = RealMagic8Ball()) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(PleaseAsk)
    val uiState = _uiState

    init {
        BusModule.bus.register(this)
    }

    fun shake() {
        if (uiState.value is PleaseAsk) {
            _uiState.value = Loading
            BusModule.bus.post(ShakeItUp)
        }
    }

    @Subscribe
    fun revealAnswer(message: MessageFromeTheAether) {
        _uiState.value = Reveal(message.answer)
        viewModelScope.launch {
            delay(5_000)
            _uiState.value = PleaseAsk
        }
    }

    override fun onCleared() {
        super.onCleared()
        BusModule.bus.unregister(this)
    }
}
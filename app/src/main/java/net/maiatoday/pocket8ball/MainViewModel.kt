package net.maiatoday.pocket8ball

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import net.maiatoday.pocket8ball.di.BusModule
import net.maiatoday.pocket8ball.di.MessageFromTheAether
import net.maiatoday.pocket8ball.di.ShakeItUp

class MainViewModel(private val eightBall: Magic8Ball = RealMagic8Ball()) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(PleaseAsk)
    val uiState = _uiState

    init {
        viewModelScope.launch {
            BusModule.bus.filter { event -> event is MessageFromTheAether }
                .collectLatest {
                    _uiState.value = Reveal((it as MessageFromTheAether).answer)
                    delay(5_000)
                    _uiState.value = PleaseAsk
                }
        }
    }

    fun shake() {
        if (uiState.value is PleaseAsk) {
            _uiState.value = Loading
            viewModelScope.launch {
                BusModule.post(ShakeItUp)
            }
        }
    }

}
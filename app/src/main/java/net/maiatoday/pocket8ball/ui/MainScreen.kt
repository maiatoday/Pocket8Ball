package net.maiatoday.pocket8ball.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.maiatoday.pocket8ball.Loading
import net.maiatoday.pocket8ball.PleaseAsk
import net.maiatoday.pocket8ball.R
import net.maiatoday.pocket8ball.Reveal
import net.maiatoday.pocket8ball.UiState
import net.maiatoday.pocket8ball.ui.theme.Pocket8BallTheme

@Composable
fun EightBallScreen(uiState: UiState, shake: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .clickable { shake() }
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ball),
            contentDescription = "the magic 8 ball",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(2f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            Crossfade(targetState = uiState, label = "uiState") { state ->
                when (state) {
                    PleaseAsk -> {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "Form a clear yes/no question in your mind and touch the ball to reveal the answer."
                        )
                    }

                    Loading -> CircularProgressIndicator()

                    is Reveal -> {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "The eight ball says: "
                            )
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = state.answer,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Pocket8BallTheme {
        EightBallScreen(Reveal("Maybe"), {})
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewLoading() {
    Pocket8BallTheme {
        EightBallScreen(Loading, {})
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultAsk() {
    Pocket8BallTheme {
        EightBallScreen(PleaseAsk, {})
    }
}
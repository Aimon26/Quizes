package com.aimon.app.quizes.fetures.score

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.ui.theme.QuizesTheme
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.uuid.Uuid

@Composable
fun ScoreScreen(
    num: Int,
    total: Int,
    viewModel: ScoreScreenViewModel= hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark)),
        contentAlignment = Alignment.Center
    ) {
        MyConfettiScreen()
        Card(
            modifier = Modifier
                .size(width = 350.dp, height = 430.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.white)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )

                Text(
                    text = "Your Score: $num / ${total}",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${(num * 100) / total}% Correct Answer",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "You attended $total questions and",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "from that $num answers are Correct",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        viewModel.addLeaderBoard((num * 100) / total)
                        navController.navigate("home")
                    Log.d("xsxsxh","${(num * 100) / total}")
                    },
                    modifier = Modifier
                ) {
                    Text("Go to Home")
                }
            }

        }
    }
}

@Composable
fun MyConfettiScreen() {
    val parties = remember {
        listOf(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(
                    0xFFFCE18A.toInt(),
                    0xFFFF726D.toInt(),
                    0xFFF4306D.toInt(),
                    0xFFB48DEF.toInt()
                ),
                position = Position.Relative(0.5, 0.3),
                emitter = Emitter(duration = 3, TimeUnit.SECONDS).max(300)
            )
        )
    }

    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = parties
    )
}

@Preview
@Composable
fun ShowScore() {
    QuizesTheme {
        Surface {
            val navController = rememberNavController()
            ScoreScreen(20,total=50, navController = navController)
        }
    }
}

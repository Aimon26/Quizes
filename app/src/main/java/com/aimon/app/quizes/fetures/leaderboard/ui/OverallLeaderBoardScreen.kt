package com.aimon.app.quizes.fetures.leaderboard.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.fetures.leaderboard.data.LeaderboardNavigate
import com.aimon.app.quizes.fetures.leaderboard.repository.Board
import com.aimon.app.quizes.ui.theme.QuizesTheme
@Composable
fun OverallLeaderBoardScreen(
    navController: NavController,
    viewModel: OverallLeaderBoardScreenViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    OverallLeaderBoardScreen(
        state = state,
        onGoClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun OverallLeaderBoardScreen(
    state: UiState,
    onGoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark))
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.teal_700), shape = RoundedCornerShape(12.dp))
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🏆 Overall Leaderboard",
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.loading == true -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.teal_700))
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.error}",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }

            else -> {
                val leaderboard = state.items
                if (leaderboard.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No data available", color = Color.Gray)
                    }
                } else {
                    Column() {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Rank", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                            Text("User", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                            Text("Score", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier,
                            userScrollEnabled = true
                        ) {
                            itemsIndexed(leaderboard) { index, item ->
                                LeaderboardItem(rank = index + 1, board = item)
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onGoClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)

                        ) {

                            Text(
                                "Go to Home", fontSize = 20.sp
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LeaderboardItem(rank: Int, board: Board) {
    val bgColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> colorResource(R.color.purple_200)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = bgColor.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "#$rank",
                fontSize = 18.sp,
                color = bgColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = board.username,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = "${board.score}",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaderboard() {
    val sample = listOf(
        Board(uuid = "User123", score = 95, timeStamp = 1L, username = "X"),
        Board(uuid = "User456", score = 90, timeStamp = 1L, username = "Y"),
        Board(uuid = "User789", score = 85, timeStamp = 1L, username = "Z")
    )
    QuizesTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010))
                .padding(12.dp)
        ) {
            Text(
                text = "🏆 Overall Leaderboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                itemsIndexed(sample) { index, item ->
                    LeaderboardItem(rank = index + 1, board = item)
                }
            }
        }
    }
}

package com.aimon.app.quizes.fetures.leaderboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aimon.app.quizes.R

@Composable
fun MonthlyLeaderBoardScreen(
    navController: NavController,
    viewModel: MonthlyLeaderBoardScreenViewModel= hiltViewModel()

){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MonthlyLeaderBoardScreen(
        state = state,
        onGoClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun MonthlyLeaderBoardScreen(
    state: MonthlyLeaderBoardScreenViewModel.MonthlyLeaderBoardUiState,
    onGoClick:()->Unit
){
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
                text = "🏆 Monthly Leaderboard",
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
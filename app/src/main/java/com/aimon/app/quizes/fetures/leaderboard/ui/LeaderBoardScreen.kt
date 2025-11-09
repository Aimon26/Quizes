package com.aimon.app.quizes.fetures.leaderboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.fetures.leaderboard.data.LeaderboardNavigate

@Composable
fun LeaderBoardScreen(
    navControllerX: NavHostController,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val startDestination = LeaderboardNavigate.OverallLeaderBoard
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.index) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Column(
        modifier = Modifier

            .background(color = colorResource(R.color.dark))
    ) {
        if (currentDestination?.route == LeaderboardNavigate.OverallLeaderBoard.route||currentDestination?.route == LeaderboardNavigate.DailyLeaderBoard.route || currentDestination?.route == LeaderboardNavigate.WeeklyLeaderBoard.route || currentDestination?.route == LeaderboardNavigate.MonthlyLeaderBoard.route) {
            PrimaryTabRow(
                selectedTabIndex = selectedDestination,
                containerColor = colorResource(R.color.light),
                contentColor = Color.White
            ) {
                LeaderboardNavigate.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        text = {
                            Text(
                                text = destination.title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }

        NavHost(
            modifier = Modifier.weight(1f).fillMaxSize(),
            navController = navController,
            startDestination = LeaderboardNavigate.OverallLeaderBoard.route
        ) {

            composable(route = LeaderboardNavigate.OverallLeaderBoard.route) {
                OverallLeaderBoardScreen(navController=navControllerX)


            }
            composable(route = LeaderboardNavigate.DailyLeaderBoard.route) {


            }

            composable(route = LeaderboardNavigate.WeeklyLeaderBoard.route) {


            }

            composable(route = LeaderboardNavigate.MonthlyLeaderBoard.route) {

            }

        }
    }
}

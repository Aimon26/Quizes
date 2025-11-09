package com.aimon.app.quizes.fetures.leaderboard.data

import com.aimon.app.quizes.fetures.homescreen.ui.HomeScreen

enum class LeaderboardNavigate(
    val route: String,
    val title: String,
    val index: Int
) {
    OverallLeaderBoard(
        route = "overall",
        title = "Overall",
        index = 0
    ),
    DailyLeaderBoard(
        route = "daily",
        title = "Daily",
        index = 1
    ),
    WeeklyLeaderBoard(
        route = "weekly",
        title = "Weekly",
        index = 2
    ),
    MonthlyLeaderBoard(
        route = "monthly",
        title = "Monthly",
        index = 3
    )


}
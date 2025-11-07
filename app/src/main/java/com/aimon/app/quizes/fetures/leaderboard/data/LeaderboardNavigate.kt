package com.aimon.app.quizes.fetures.leaderboard.data

enum class LeaderboardNavigate(
    val route: String,
    val title: String,
    val index: Int
) {
    DailyLeaderBoard(
        route = "daily",
        title = "Daily",
        index = 0
    ),
    WeeklyLeaderBoard(
        route = "weekly",
        title = "Weekly",
        index = 1
    ),
    MonthlyLeaderBoard(
        route = "monthly",
        title = "Monthly",
        index = 2
    )
}
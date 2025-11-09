package com.aimon.app.quizes.fetures.leaderboard.repository

import com.aimon.app.quizes.fetures.score.repository.LeaderBoard

interface LeaderBoardRepository {
    suspend fun showLeaderBoard():List<Board>
}
data class Board(
    val uuid: String,
    val score: Long
)


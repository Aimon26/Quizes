package com.aimon.app.quizes.fetures.leaderboard.repository

import com.aimon.app.quizes.fetures.leaderboard.data.LeaderBoardType

interface LeaderBoardRepository {
    suspend fun showLeaderBoard(type: LeaderBoardType): Result<List<Board>>
}
data class Board(
    val uuid: String,
    val score: Long,
    val timeStamp: Long
)




package com.aimon.app.quizes.fetures.score.repository

import com.google.firebase.auth.FirebaseAuth

interface FireBaseRepository {
    suspend fun addLeaderBoard(
        leaderBoard: LeaderBoard
    ): Result<Any>
}


data class LeaderBoard(
    val uuid: String,
    val score: Long,
    val timeStamp: Long
)
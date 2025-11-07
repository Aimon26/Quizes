package com.aimon.app.quizes.fetures.score.repository

import com.google.firebase.auth.FirebaseAuth

interface FireBaseRepository {
    fun addLeaderBoard(
        leaderBoard: LeaderBoard
    )
//    fun showLeaderBoard():List<LeaderBoard>
}


data class LeaderBoard(
    val uuid: String,
    val score: Int
)
package com.aimon.app.quizes.fetures.score.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class FireBaseRepositoryImpl @Inject constructor(

): FireBaseRepository {
    private val database = Firebase.firestore
    var lst = mutableListOf<LeaderBoard>()

    override fun addLeaderBoard(
        leaderBoard: LeaderBoard
    ) {
        database.collection("scores")
            .add(leaderBoard)
    }

//    override fun showLeaderBoard(): List<LeaderBoard> {
//
//
//    }
}


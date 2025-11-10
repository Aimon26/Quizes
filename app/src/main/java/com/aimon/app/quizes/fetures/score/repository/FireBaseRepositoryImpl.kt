package com.aimon.app.quizes.fetures.score.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class FireBaseRepositoryImpl @Inject constructor(

): FireBaseRepository {
    private val database = Firebase.firestore

    override suspend fun addLeaderBoard(
        leaderBoard: LeaderBoard
    ): Result<Unit> {
        try {
            database.collection("scores")
                .add(leaderBoard).await()
            return Result.success(Unit)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }
}


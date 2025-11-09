package com.aimon.app.quizes.fetures.leaderboard.repository

import android.util.Log
import com.aimon.app.quizes.fetures.score.repository.LeaderBoard
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderBoardRepositoryImpl @Inject constructor(

) : LeaderBoardRepository {
    override suspend fun showLeaderBoard(): List<Board> {
        val db = Firebase.firestore
        var lst = mutableListOf<Board>()
        try {
            db.collection("scores").get().await().map {
                lst.add(
                    Board(
                        uuid = it.data["uuid"] as String,
                        score = it.data["score"] as Long
                    )
                )
            }
        }
        catch (e: Exception) {
            Log.d("error", e.toString())

        }
        return lst.sortedByDescending { it.score }
    }
}

package com.aimon.app.quizes.fetures.score

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aimon.app.quizes.fetures.score.repository.FireBaseRepository
import com.aimon.app.quizes.fetures.score.repository.LeaderBoard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ScoreScreenViewModel @Inject constructor(
    private val repository: FireBaseRepository
) : ViewModel(){
    fun addLeaderBoard(
        score: Long
    ){
        repository.addLeaderBoard(
            LeaderBoard(
                score = score,
                uuid = FirebaseAuth.getInstance().currentUser!!.uid
            )

        )
        Log.d("gshg","${score} and ${FirebaseAuth.getInstance().currentUser!!.uid}")


    }
}
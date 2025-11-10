package com.aimon.app.quizes.fetures.score

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aimon.app.quizes.fetures.score.repository.FireBaseRepository
import com.aimon.app.quizes.fetures.score.repository.LeaderBoard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ScoreScreenViewModel @Inject constructor(
    private val repository: FireBaseRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    private val _scoreSaveState = Channel<Boolean?>()
    val scoreSaveState = _scoreSaveState.receiveAsFlow()

    fun addLeaderBoard(
        score: Long
    ){
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            val result = repository.addLeaderBoard(
                LeaderBoard(
                    score = score,
                    uuid = FirebaseAuth.getInstance().currentUser!!.uid,
                    timeStamp = System.currentTimeMillis()
                )
            )
            if(result.isSuccess){
                _scoreSaveState.send(true)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
            if(result.isFailure){
                _uiState.update { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}

data class ScoreUiState(
    val isLoading: Boolean = false
)
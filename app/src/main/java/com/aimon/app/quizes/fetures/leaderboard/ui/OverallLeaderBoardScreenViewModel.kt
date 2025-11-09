package com.aimon.app.quizes.fetures.leaderboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aimon.app.quizes.fetures.leaderboard.repository.Board
import com.aimon.app.quizes.fetures.leaderboard.repository.LeaderBoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverallLeaderBoardScreenViewModel @Inject constructor(
    val repository: LeaderBoardRepository

) : ViewModel() {
    private val _uiState= MutableStateFlow<UiState>(UiState())
    var uiState=_uiState.asStateFlow()

    init {
        getLeaderBoard()
    }
    fun getLeaderBoard(){
        viewModelScope.launch {
            try{
                _uiState.update {
                    state -> state.copy(loading = true)
                }
                repository.showLeaderBoard()
                _uiState.update {
                    state -> state.copy(loading = false, success = repository.showLeaderBoard().toMutableList())


                }
            }
            catch (e: Exception){
                _uiState.update {
                    state -> state.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }

        }

    }

}

data class UiState(
    var loading: Boolean=false,
    var success: MutableList<Board> = mutableListOf<Board>(),
    var error: String?=null

    )
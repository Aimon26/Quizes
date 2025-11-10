package com.aimon.app.quizes.fetures.leaderboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aimon.app.quizes.fetures.leaderboard.data.LeaderBoardType
import com.aimon.app.quizes.fetures.leaderboard.repository.Board
import com.aimon.app.quizes.fetures.leaderboard.repository.LeaderBoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyLeaderBoardScreenViewModel @Inject constructor(
    val repository: LeaderBoardRepository

) : ViewModel(){
    private val _uiState= MutableStateFlow< MonthlyLeaderBoardUiState>(MonthlyLeaderBoardUiState())
    val uiState=_uiState.asStateFlow()
    init{
        getLeaderBoard(LeaderBoardType.Monthly)

    }
    fun getLeaderBoard(type: LeaderBoardType){
        viewModelScope.launch {
            try{
                _uiState.update {
                        state -> state.copy(loading = true)
                }
                val result= repository.showLeaderBoard(type = LeaderBoardType.Monthly)
                if (result.isSuccess){
                    _uiState.update {
                            state -> state.copy(loading = false, items = result.getOrDefault(emptyList()))
                    }

                }
            }
            catch (e: Exception){
                _uiState.update {state ->
                    state.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }
        }


    }
    data class MonthlyLeaderBoardUiState(
        val loading: Boolean=false,
        val items:List<Board> = listOf<Board>(),
        val error: String?=null

    )


}


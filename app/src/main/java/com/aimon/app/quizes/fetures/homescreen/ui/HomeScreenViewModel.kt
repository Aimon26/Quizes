package com.aimon.app.quizes.fetures.homescreen.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.PathSegment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aimon.app.quizes.core.constants.EventHome
import com.aimon.app.quizes.core.constants.InitialEventHome
import com.aimon.app.quizes.fetures.authentication.ui.AuthState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(

): ViewModel()
{
    private val auth: FirebaseAuth= FirebaseAuth.getInstance()
    private val _authState= Channel<AuthState?>()
    val authState=_authState.receiveAsFlow()
    val _homeState= MutableStateFlow(InitialEventHome())
    val homeState=_homeState
    fun signOut(){
        viewModelScope.launch {
            auth.signOut()
            _authState.send(AuthState.Unauthenticated)
        }
    }
    fun setNoOfQuiz(value: String){
        viewModelScope.launch {
            _homeState.update {
                it.copy(NoOfQuiz = value.toInt())
            }
        }
    }
    fun setCategory(value:String){
        viewModelScope.launch {
            _homeState.update {
                it.copy(Category = value)
            }
        }
    }
    fun setDifficulty(value:String){
        viewModelScope.launch {
            _homeState.update {
                it.copy(Difficulty = value)
            }
        }
    }
    fun setType(value:String){
        viewModelScope.launch {
            _homeState.update {
                it.copy(Type = value)
            }
        }
    }

}


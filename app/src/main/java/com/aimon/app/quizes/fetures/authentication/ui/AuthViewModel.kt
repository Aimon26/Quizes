package com.aimon.app.quizes.fetures.authentication.ui

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

): ViewModel(){
    private val auth: FirebaseAuth= FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _authState= Channel<AuthState?>()
    val authState=_authState.receiveAsFlow()

    fun isLoggedIn(): Boolean{
        return auth.currentUser!=null

    }
    fun login(
        mail: String,
        pass: String
    ){
        viewModelScope.launch {
            if (mail.isEmpty() || pass.isEmpty()) {
                _authState.send(AuthState.Error("Email or Password field can't be empty"))
            }
            else {
                _authState.send(AuthState.Loading)
                auth.signInWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            _authState.send(AuthState.Authenticated)
                        }
                    }
                    .addOnFailureListener {
                        viewModelScope.launch {
                            _authState.send(AuthState.Error(it.message.toString()))
                        }
                    }
            }
        }
    }
    fun signUp(
        age:String,
        mail: String,
        pass: String,
        username: String
    ){
        viewModelScope.launch {
            if (mail.isEmpty() || pass.isEmpty()) {
                _authState.send(AuthState.Error("Email or Password field can't be empty"))
            } else {
                _authState.send(AuthState.Loading)
                auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            firestore.collection("users").document(auth.currentUser!!.uid)
                                .set(hashMapOf(
                                    "username" to username,
                                    "email" to mail,
                                    "age " to age
                                )).await()
                            _authState.send(AuthState.Authenticated)
                        }
                    }
                    .addOnFailureListener {
                        viewModelScope.launch {
                            _authState.send(AuthState.Error(it.message.toString()))
                        }
                    }
            }
        }
    }


}

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()

}
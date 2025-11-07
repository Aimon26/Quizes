package com.aimon.app.quizes.fetures.quizscreen.ui
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aimon.app.quizes.core.constants.Constants
import com.aimon.app.quizes.fetures.quizscreen.domain.model.Question
import com.aimon.app.quizes.fetures.quizscreen.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizScreeenViewModel @Inject constructor(
    val repository: QuizRepository,
    savedStateHandle: SavedStateHandle

): ViewModel(){
   private val _uiState =MutableStateFlow<QuizUiState>(QuizUiState())
    val uiState=_uiState.asStateFlow()
    private val amount: Int = savedStateHandle.get<Int>("no") ?: 0
    private val category: String = savedStateHandle.get<String>("cat") ?:""
    private val difficulty: String = savedStateHandle.get<String>("dif") ?: ""
    private val type: String = savedStateHandle.get<String>("type") ?: ""

    init {
        showQuiz()
    }
    var dif =" "
    var cat=" "


    fun onNextClick(){
        _uiState.update { state ->
            state.copy(
                crrnt = state.crrnt + 1
            )
        }

    }

    fun onPreviousClick(){
        _uiState.update { state ->
            state.copy(
                crrnt = _uiState.value.crrnt-1
            )
        }

    }

    fun onOptionSelect(option: String) {
        val currentState = _uiState.value
        val question = currentState.quizes[currentState.crrnt]
        val isCorrect = question.correctAnswer == option
            _uiState.update { state ->
                state.copy(
                    score = if (isCorrect) state.score + 1 else state.score,
                    quizes = state.quizes.toMutableList().apply {
                        this[state.crrnt] = question.copy(
                            selectedAnswer = option

                        )
                    }
                )
            }
        }

    fun showQuiz(){
        if(difficulty=="Easy")
            dif="easy"
        else if(difficulty=="Hard")
            dif="hard"
        else
            dif="medium"
        if(type=="Multiple Choice")
            cat="multiple"
        else
            cat="boolean"
        viewModelScope.launch {
            try{
                _uiState.update { state ->
                    state.copy(isLoading =true)
                }
                Constants.categoriesMap[category]?.let { value ->
                    var result = repository.getQuiz(amount, value, dif, cat)

                    _uiState.update { state ->
                        state.copy(isLoading = false, quizes = result.toMutableList())
                    }
                    Log.d("API_SUCCESS", "Quiz data received successfully")
                }
            } catch (e: Exception){
                _uiState.update {
                    state -> state.copy(
                        isLoading = false,
                        isError = e.message
                    )
                }
                Log.d("API_Failed", "Quiz data error")
                e.printStackTrace()
            }
        }
    }
}


data class QuizUiState(
    val isLoading: Boolean=false,
    val quizes: MutableList<Question> = mutableListOf<Question>(),
    val isError: String?=null,
    var crrnt:Int=0,
    var score:Int=0
)
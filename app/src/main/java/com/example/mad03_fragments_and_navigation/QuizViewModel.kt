package com.example.mad03_fragments_and_navigation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad03_fragments_and_navigation.models.Question
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue

class QuizViewModel: ViewModel() {

    val index = MutableLiveData<Int>()
    val questions = MutableLiveData<List<Question>>()
    val quentionsCount = MutableLiveData<Int>()
    val score = MutableLiveData<Int>()
    val running = MutableLiveData<Boolean>()
    val question = MutableLiveData<Question>()

    init {
        Log.i("QuizViewModel", "initialized")
        index.value = 0
        questions.value = QuestionCatalogue.exampleCatalogue
        quentionsCount.value = questions.value!!.count()
        score.value = 0
        running.value = true
        question.value = questions.value!![index.value!!]
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("QuizViewModel", "destroyed")
    }

    fun nextQuestion(selectedAnswerID: Int) {
        val correct = if (question.value!!.answers[selectedAnswerID].isCorrectAnswer) 1 else 0
        score.value = score.value!!.plus(correct)
        index.value = index.value!!.plus(1)
        if (index.value!! < 3) {
            question.value = questions.value!![index.value!!]
            return
        }
        running.value = false
    }
}
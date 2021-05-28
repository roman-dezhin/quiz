package com.example.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class QuestionsViewModel(application: Application) : AndroidViewModel(application) {

    private val questions = Datastore.fetch(getApplication())

    private val _currentIndex = MutableLiveData<Int>().apply {
        value = 0
    }
    val currentIndex: LiveData<Int> get() = _currentIndex

    private val _isFinished = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isFinished: LiveData<Boolean> get() = _isFinished

    private val _isAnswerSelected = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isAnswerSelected: LiveData<Boolean> get() = _isAnswerSelected

    private var score = 0

    fun getCurrentQuestion() = questions[currentIndex.value!!]

    fun getQuestionsCount(): Int = questions.size

    // check user's answer
    fun checkUserAnswer(answerIndex: Int): Boolean {
        // inform about the need to disable the answers buttons
        _isAnswerSelected.value = true
        return if (questions[currentIndex.value!!].correctAnswerIndex == answerIndex) {
            score++
            true
        } else
            false
    }

    // go to next question if it exists or go to result screen
    fun next() {
        currentIndex.value.let {
            if (isNextQuestionExist())
                _currentIndex.value = it?.plus(1)
            else
                gotoResult()
        }
        _isAnswerSelected.value = false
    }

    fun getRightAnswers() = score

    // check if exist next question
    private fun isNextQuestionExist(): Boolean {
        return currentIndex.value!! < getQuestionsCount() - 1
    }

    // go to result screen
    private fun gotoResult() {
        _isFinished.value = true
    }
}
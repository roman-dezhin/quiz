package com.example.quiz

data class QuestionModel(
    val questionText: String,
    val answers: List<Answer>,
    val correctAnswerIndex: Int
)

data class QuestionsModel(
    val questions: List<QuestionModel>
)

data class Answer(
    val answerText: String
)
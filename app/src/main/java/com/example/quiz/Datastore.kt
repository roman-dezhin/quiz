package com.example.quiz

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type


object Datastore {
    fun fetch(context: Context): List<QuestionModel> {

        //read text from questions.json file in assets
        val jsonString = context.assets.open("questions.json")
            .bufferedReader().use { it.readText() }

        //use moshi for converting json string into list of QuestionModel
        val moshi = Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // create own type for moshi adapter
        val listOfQuestionModelType: Type = Types.newParameterizedType(
            List::class.java,
            QuestionModel::class.java
        )
        val jsonAdapter: JsonAdapter<QuestionsModel> = moshi.adapter(QuestionsModel::class.java)
        val questions = jsonAdapter.fromJson(jsonString)?.questions

        // return list of QuestionModel or empty list if jsonAdapter.fromJson() return null
        return questions ?: emptyList()
    }
}
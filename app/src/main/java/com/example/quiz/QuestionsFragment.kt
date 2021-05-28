package com.example.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.quiz.databinding.FragmentQuestionsBinding

class QuestionsFragment : Fragment() {

    private lateinit var viewModel: QuestionsViewModel

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentIndex.observe(viewLifecycleOwner,  {
            // prepare screen for displaying current question and answers
            clearScreen()

            // get current question
            val question = viewModel.getCurrentQuestion()

            // display current question
            displayQuestion(question)

            // display current question's answers
            displayAnswers(question)

        })

        viewModel.isAnswerSelected.observe(viewLifecycleOwner, {
            // when user selected answer, disable all answer buttons
            if (it) {
                val layout = binding.questionLayout
                for (i in 1 until layout.childCount) {
                    val child = layout.getChildAt(i)
                    child.isEnabled = false
                }
            }
        })

        viewModel.isFinished.observe(viewLifecycleOwner,  {
            // if all questions were answered then go to result screen
            if (it) {
                val action = QuestionsFragmentDirections
                    .actionQuestionsFragmentToResultFragment(
                        viewModel.getQuestionsCount(),
                        viewModel.getRightAnswers()
                    )
                findNavController().navigate(action)
            }
        })
    }

    private fun clearScreen() {
        // remove question TextView ans answers buttons
        binding.questionLayout.removeAllViews()
        // hide next button
        binding.nextButton.visibility = View.INVISIBLE
    }

    private fun displayQuestion(question: QuestionModel) {
        val questionTextView = TextView(context)
        questionTextView.text = question.questionText
        questionTextView.textSize = 20f
        binding.questionLayout.addView(questionTextView)
    }

    private fun displayAnswers(question: QuestionModel) {
        val answersCount = question.answers.size
        for (i in 0 until answersCount) {
            val button = Button(context)
            button.id = i + 1
            button.text = question.answers[i].answerText
            button.setOnClickListener {
                // check user's answer and color the button
                if (viewModel.checkUserAnswer(it.id - 1)) {
                    it.setBackgroundColor(Color.GREEN)
                } else {
                    it.setBackgroundColor(Color.RED)
                }
                // show next button
                binding.nextButton.visibility = View.VISIBLE
                binding.nextButton.setOnClickListener{
                    viewModel.next()
                }
            }
            binding.questionLayout.addView(button)
        }
    }
}
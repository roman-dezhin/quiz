package com.example.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.totalQuestions.text = getString(R.string.total_questions, args.totalQuestions)
        binding.rightAnswers.text = getString(R.string.right_answers, args.rightAnswers)
        val percents = 100f * args.rightAnswers / args.totalQuestions
        binding.persents.text = getString(R.string.percents, percents.toInt())

        binding.againButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_startFragment)
        }
    }
}
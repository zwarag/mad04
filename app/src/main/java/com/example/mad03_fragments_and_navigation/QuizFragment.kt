package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizBinding
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel
    var selectedAnswer: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        binding.quizViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnNext.setOnClickListener {
            nextQuestion()
        }

        viewModel.running.observe(
            viewLifecycleOwner,
            { running ->
                if (!running) findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToQuizEndFragment(viewModel.score.value!!)
                )
            })

        setCalculateEvent()

        return binding.root
    }

    private fun setCalculateEvent() {
        binding.answerBox.setOnCheckedChangeListener { group, checkedId ->
            selectedAnswer = group.indexOfChild(group.findViewById(checkedId))
        }
    }

    private fun unsetCalculateEvent() {
        binding.answerBox.setOnCheckedChangeListener { _, _ -> }
    }

    private fun nextQuestion() {
        if (selectedAnswer == -1) {
            Toast.makeText(
                requireContext(),
                "Select an answer before continuing!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        unsetCalculateEvent()
        viewModel.nextQuestion(selectedAnswer)
        binding.answerBox.clearCheck()
        setCalculateEvent()
    }
}
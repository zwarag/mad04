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
import androidx.navigation.fragment.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizBinding
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val questions = QuestionCatalogue().defaultQuestions    // get a list of questions for the game
    private var score = 0                                           // save the user's score
    private var correct = 0
    private var index = 0                                           // index for question data to show

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        binding.index = index
        binding.questionsCount = questions.size
        binding.question = questions[index]

        binding.btnNext.setOnClickListener {
            nextQuestion()
        }

        setCalculateEvent()

        return binding.root
    }

    private fun setCalculateEvent() {
        binding.answerBox.setOnCheckedChangeListener { group, checkedId -> correct = if (questions[index].answers[group.indexOfChild(group.findViewById(checkedId))].isCorrectAnswer) 1 else 0 }
    }

    private fun unsetCalculateEvent() {
        binding.answerBox.setOnCheckedChangeListener { _, _ -> }
    }

    private fun nextQuestion() {
        // get selected answer
        // check if is correct answer
        Log.i("QuizFragment", "score: $score")
        Log.i("QuizFragment", "correct: $correct")
        Log.i("QuizFragment", "index: $index")
        // update score
        Log.i("QuizFragment", binding.answerBox.checkedRadioButtonId.toString())
        if (binding.answerBox.checkedRadioButtonId == -1) {
            Toast.makeText(requireContext(), "Select an answer before continuing!", Toast.LENGTH_SHORT).show()
            return
        }
        score += correct
        unsetCalculateEvent()
        binding.answerBox.clearCheck()
        setCalculateEvent()
        correct = 0
        binding.index = ++index
        // check if there are any questions left
        if (index < 3) {
            // show next question
            binding.question = questions[index]
            return
        }
        // navigate to QuizEndFragment
        findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizEndFragment(score))
    }
}
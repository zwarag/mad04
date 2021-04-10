package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizEndBinding


class QuizEndFragment : Fragment() {
    private lateinit var binding: FragmentQuizEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_end, container, false)

        binding.btnRestart.setOnClickListener { view: View -> view.findNavController().navigate(QuizEndFragmentDirections.actionQuizEndFragmentToQuizFragment()) }

        // get score from navigation arguments
        val args = QuizEndFragmentArgs.fromBundle(requireArguments())
        val score = args.score
        Log.i("QuizFragmentEnd", "score: $score")

        // show score
        binding.textView7.text = "$score/3"

        return binding.root
    }
}
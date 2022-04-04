/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding
import com.example.android.guesstheword.screens.score.ScoreViewModelFactory

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var gameVM:GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        gameVM = ViewModelProvider(this).get(GameViewModel::class.java)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        gameVM.resetList()
        gameVM.nextWord()

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }
        updateScoreText()
        updateWordText()
        return binding.root

    }

    /** Methods for updating the UI **/

    private fun updateWordText(){
        binding.wordText.text = gameVM.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = gameVM.score.toString()
    }

    fun onSkip() {
        gameVM.onSkip()
        updateWordText()
        updateScoreText()
    }

    fun onCorrect() {
        gameVM.onCorrect()
        updateWordText()
        updateScoreText()
    }

    private fun onEndGame(){

        Toast.makeText(
            activity,
            "Game has just finished",
            Toast.LENGTH_LONG).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = gameVM.score
        //ScoreViewModelFactory(gameVM.score)
        NavHostFragment.findNavController(this).navigate(action)
    }
}

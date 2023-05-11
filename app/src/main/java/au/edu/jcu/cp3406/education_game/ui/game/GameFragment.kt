package au.edu.jcu.cp3406.education_game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)


        return binding.root
    }


    override fun onResume() {
        super.onResume()

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.drawableResId.observe(this){
            drawableResId -> binding.currentAnimal.setImageResource(drawableResId)
        }

        binding.guessButton.setOnClickListener{guessAnimal()}
    }

    private fun guessAnimal() {
        val guess = binding.guessInputEditText.text.toString()

        if (viewModel.checkGuess(guess)){
            // no errors
        }
        // if game over
        // there was an error
    }
}
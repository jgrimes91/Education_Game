package au.edu.jcu.cp3406.education_game.ui.game

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import au.edu.jcu.cp3406.education_game.database.ScoreBoardDatabase
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory
        val dataSource = ScoreBoardDatabase.getInstance(application).scoreboardDatabaseDao
        val viewModelFactory = GameViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        val gameViewModel =
            (ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java))
        binding.gameViewModel = gameViewModel

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        binding.lifecycleOwner = this

        binding.maxAnimalCount = MAX_NO_OF_ANIMALS

        viewModel.drawableResId.observe(this) { drawableResId ->
            binding.currentAnimal.setImageResource(drawableResId)
        }

        binding.guessButton.setOnClickListener { guessAnimal() }


    }

    private fun guessAnimal() {
        val guess = binding.guessInputEditText.text.toString()

        viewModel.checkGuess(guess)
        if (!viewModel.nextAnimal()) {
            showFinalScore()
        }
    }

    private fun showFinalScore() {
        var titleMessage = ""

        if (viewModel.score.value!! >= 20) {
            titleMessage = "Nice try!"
        } else if (viewModel.score.value!! == 30) {
            titleMessage = "Well done!"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleMessage)
            .setMessage("You scored ${viewModel.score.value} points")
            .setCancelable(false)
            .setNegativeButton("Back to home") { _, _ -> returnToTitle() }
            .setPositiveButton("Play again") { _, _ ->
                viewModel.resetData()
            }.show()
    }

    private fun returnToTitle() {
        findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
    }
}
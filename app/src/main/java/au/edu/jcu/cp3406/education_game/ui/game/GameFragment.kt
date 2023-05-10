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
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameViewModel = viewModel
        binding.maxNoOfAnimals = MAX_NO_OF_ANIMALS

        binding.lifecycleOwner = viewLifecycleOwner

    }

    /**
     * Not the best way to do this but I can't think of another way. Maybe ask Jase at the next help session?
     */
    fun displayAnimals(){
        when(viewModel.currentAnimal){
            "cow" -> binding.currentAnimalImageViewOne.setImageResource(R.drawable.cow)
            "chicken" -> binding.currentAnimalImageViewOne.setImageResource(R.drawable.chicken)
            "pig" -> binding.currentAnimalImageViewOne.setImageResource(R.drawable.pig)
        }
        when(viewModel.secondAnimal){
            "cow" -> binding.currentAnimalImageViewTwo.setImageResource(R.drawable.cow)
            "chicken" -> binding.currentAnimalImageViewTwo.setImageResource(R.drawable.chicken)
            "pig" -> binding.currentAnimalImageViewTwo.setImageResource(R.drawable.pig)
        }
    }
}
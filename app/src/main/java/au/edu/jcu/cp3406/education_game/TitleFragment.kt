package au.edu.jcu.cp3406.education_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import au.edu.jcu.cp3406.education_game.ui.game.GameViewModel
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    private lateinit var binding: FragmentTitleBinding

    val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.playButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }
        binding.settingsButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_settingsFragment)
        }
        binding.highScoreButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_scoreBoardFragment)
        }
    }
}
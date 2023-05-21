package au.edu.jcu.cp3406.education_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.education_game.ui.game.GameViewModel
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentScoreBoardBinding


class ScoreBoardFragment : Fragment() {

    private lateinit var binding: FragmentScoreBoardBinding
    val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_board, container, false)


        return binding.root
    }

    override fun onResume() {
        super.onResume()


    }
}
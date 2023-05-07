package au.edu.jcu.cp3406.education_game.game

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.edu.jcu.cp3406.educationgame.R

class GameViewModel : Fragment() {

    companion object {
        fun newInstance() = GameViewModel()
    }

    private lateinit var viewModel: GameViewModelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
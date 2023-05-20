package au.edu.jcu.cp3406.education_game
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.room.Room
import au.edu.jcu.cp3406.education_game.database.Player
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.education_game.ui.game.GameViewModel
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var difficulty: Int = 0

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var gameViewModel: GameViewModel

    private lateinit var database: PlayerDatabase
    private lateinit var playerDatabaseDao: PlayerDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        database =
            Room.databaseBuilder(requireContext(), PlayerDatabase::class.java, "player_table")
                .createFromAsset("player_table").build()


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.difficultySelection.setOnCheckedChangeListener { _, _ ->
            changeDifficulty()
        }

        binding.saveChanges.setOnClickListener { view: View ->
            saveSettings()
            view.findNavController().navigate(R.id.action_settingsFragment_to_titleFragment)
        }
    }

    private fun saveSettings() {
        gameViewModel.viewModelScope.launch {
            val playerName = binding.usernameContainer.toString()
            val playerDifficulty = changeDifficulty()

            val player = Player(name = playerName, difficulty = playerDifficulty)
            playerDatabaseDao.insertPlayer(player)
        }
    }

    private fun changeDifficulty(): Int {
        difficulty = when (binding.difficultySelection.checkedRadioButtonId) {
            R.id.easyMode -> 1
            R.id.normalMode -> 2
            else -> 3
        }
        return difficulty
    }

}
package au.edu.jcu.cp3406.education_game

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import au.edu.jcu.cp3406.education_game.database.Player
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment() : Fragment() {
    private var difficulty: Int = 0

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var database: PlayerDatabase
    private lateinit var playerDatabaseDao: PlayerDatabaseDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        database = PlayerDatabase.getInstance(requireContext())
        playerDatabaseDao = database.playerDatabaseDao

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

        binding.usernameInput.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide keyboard
            val inputMethodManager =
                view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false

    }

    private fun saveSettings() {
        lifecycleScope.launch {
            val playerName = binding.usernameInput.text.toString()
            val playerDifficulty = changeDifficulty()

            val player = Player(name = playerName, difficulty = playerDifficulty, score = 0)
            playerDatabaseDao.insertPlayer(player)
        }

//        viewModel.viewModelScope.launch {
//            val playerName = binding.usernameInput.toString()
//            val playerDifficulty = changeDifficulty()
//
//            val player = Player(name = playerName, difficulty = playerDifficulty)
//            playerDatabaseDao.insertPlayer(player)
//        }

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
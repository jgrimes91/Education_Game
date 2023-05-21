package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao

class GameViewModelFactory(
    private val dataSource: PlayerDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
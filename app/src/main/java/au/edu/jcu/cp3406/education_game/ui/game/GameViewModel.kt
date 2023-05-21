package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406.education_game.database.Player
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.educationgame.R
import kotlinx.coroutines.launch

class GameViewModel(
    dataSource: PlayerDatabaseDao
) : ViewModel() {

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentAnimalCount = MutableLiveData(0)
    val currentAnimalCount: LiveData<Int> get() = _currentAnimalCount

    private val _drawableResId = MutableLiveData<Int>()
    val drawableResId: MutableLiveData<Int> get() = _drawableResId

    private var animalList: MutableList<String> = mutableListOf()

    private val _currentAnimalModified = MutableLiveData<String>()
    val currentAnimalModified: MutableLiveData<String> get() = _currentAnimalModified

    private lateinit var currentAnimal: String

    private var player = MutableLiveData<Player?>()

    private val _difficulty = MutableLiveData<Int>(0)
    val difficulty: LiveData<Int> get() = _difficulty

    val database = dataSource

    private val playerId: Long = 0L

    init {
        getNextAnimal()
//        initialisePlayer()
//        getDifficulty()
    }

//    private fun getDifficulty() {
//        TODO("Not yet implemented")
//    }

    private fun initialisePlayer() {
        viewModelScope.launch {
            player.value = getPlayerFromDatabase()
            insertPlayer(player.value!!)
        }
    }

    private suspend fun getPlayerFromDatabase(): Player {
        return database.getPlayer()
    }

    private suspend fun insertPlayer(player: Player){
        database.insertPlayer(player)
    }

    private suspend fun updatePlayer(player: Player){
        database.updatePlayer(player)
    }

    fun resetData() {
        _score.value = 0
        _currentAnimalCount.value = 0
        animalList.clear()
        nextAnimal()
    }

    fun checkGuess(guess: String) {
        if (guess == currentAnimal) {
            increaseScore()
        } else {
            _score.value = score.value
        }
    }

    private fun increaseScore() {
        _score.value = (score.value)?.plus(SCORE_INCREASE)
    }

    private fun getNextAnimal() {
        currentAnimal = allAnimals.random()

        val replacementChar = '_'
        val index = (currentAnimal.indices).random()
        currentAnimalModified.value =
            StringBuilder(currentAnimal).apply { setCharAt(index, replacementChar) }.toString()

        if (animalList.contains(currentAnimal)) {
            getNextAnimal()
        } else {
            _currentAnimalCount.value = (_currentAnimalCount.value)?.inc()
            animalList.add(currentAnimal)

            getAnimalImage()
        }
    }


    fun getAnimalSoundResId(): Int {

        val soundResId = when (currentAnimal) {
            "cow" -> R.raw.cow
            "chicken" -> R.raw.chicken
            "pig" -> R.raw.pig
            else -> R.raw.error
        }
        return soundResId
    }


    private fun getAnimalImage() {

        if (currentAnimal == allAnimals[0]) {
            drawableResId.value = R.drawable.cow
        }
        if (currentAnimal == allAnimals[1]) {
            drawableResId.value = R.drawable.chicken
        }
        if (currentAnimal == allAnimals[2]) {
            drawableResId.value = R.drawable.pig
        }
    }

    fun nextAnimal(): Boolean {
        return if (currentAnimalCount.value!! < MAX_NO_OF_ANIMALS) {
            getNextAnimal()
            true
        } else {
//            savePlayer()
            false

        }
    }

    private fun savePlayer() {
        viewModelScope.launch{
            updatePlayer(player.value!!)
        }
    }
}

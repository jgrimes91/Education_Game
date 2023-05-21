package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.educationgame.R

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


//    private val _difficulty = MutableLiveData<Int>(0)
//    val difficulty: LiveData<Int> get() = _difficulty

    val database = dataSource

    init {
        getNextAnimal()
    }

    /**
     * Resets data for player if they want to play again.
     */
    fun resetData() {
        _score.value = 0
        _currentAnimalCount.value = 0
        animalList.clear()
        nextAnimal()
    }

    /**
     * Checks the users guess.
     * If correct, increment the score. If not, score remains the same.
     */
    fun checkGuess(guess: String) {
        if (guess == currentAnimal) {
            increaseScore()
        } else {
            _score.value = score.value
        }
    }

    /**
     * Increasing the score.
     */
    private fun increaseScore() {
        _score.value = (score.value)?.plus(SCORE_INCREASE)
    }

    /**
     * Gets random animal from list, added to new animal list so that same animal isn't repeated in
     * next round.
     */
    private fun getNextAnimal() {
        currentAnimal = allAnimals.random()

        val replacementChar = '_'
        val index = (currentAnimal.indices).random()
        currentAnimalModified.value =
            StringBuilder(currentAnimal).apply { setCharAt(index, replacementChar) }.toString()     // Replaces letters with character '_' in a random position

        if (animalList.contains(currentAnimal)) {
            getNextAnimal()
        } else {
            _currentAnimalCount.value = (_currentAnimalCount.value)?.inc()
            animalList.add(currentAnimal)

            getAnimalImage()
        }
    }

    /**
     * Gets the sound resource id for the current animal
     */
    fun getAnimalSoundResId(): Int {

        val soundResId = when (currentAnimal) {
            "cow" -> R.raw.cow
            "chicken" -> R.raw.chicken
            "pig" -> R.raw.pig
            else -> R.raw.error
        }
        return soundResId
    }


    /**
     * Gets the animal drawable image id for the current animal
     */
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

    /**
     * Checks the count against the number of animals for the game.
     */
    fun nextAnimal(): Boolean {
        return if (currentAnimalCount.value!! < MAX_NO_OF_ANIMALS) {
            getNextAnimal()
            true
        } else {
            false

        }
    }
}
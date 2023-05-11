package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.edu.jcu.cp3406.educationgame.R

class GameViewModel : ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentAnimalCount = MutableLiveData(0)
    val currentAnimalCount: LiveData<Int> get() = _currentAnimalCount

    private val _drawableResId = MutableLiveData<Int>()
    val drawableResId: MutableLiveData<Int> get() = _drawableResId

    private var animalList: MutableList<String> = mutableListOf()

    private lateinit var currentAnimalModified: String

    private lateinit var currentAnimal: String

    init {
        getNextAnimal()
    }

    fun checkGuess(guess: String): Boolean {
        if (guess.equals(currentAnimal, true)) {
            increaseScore()
            return true
        }
        return false
    }

    private fun increaseScore() {
        _score.value = (score.value)?.plus(SCORE_INCREASE)
    }

    private fun getNextAnimal() {
        currentAnimal = allAnimals.random()

        val replacementChar = '_'
        val index = (currentAnimal.indices).random()
        currentAnimalModified =
            StringBuilder(currentAnimal).apply { setCharAt(index, replacementChar) }.toString()

        if (animalList.contains(currentAnimal)) {
            getNextAnimal()
        } else {
            _currentAnimalCount.value = (_currentAnimalCount.value)?.inc()
            animalList.add(currentAnimal)
            getImageResource()
        }
    }

    private fun getImageResource() {
        if (currentAnimal == "cow") {
            drawableResId.value = R.drawable.cow
        }
        if (currentAnimal == "chicken") {
            drawableResId.value = R.drawable.chicken
        }
        if (currentAnimal == "pig") {
            drawableResId.value = R.drawable.pig
        }
    }

    fun nextAnimal(): Boolean {
        return if (currentAnimalCount.value!! < MAX_NO_OF_ANIMALS) {
            getNextAnimal()
            true
        } else {
            false
        }
    }
}

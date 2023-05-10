package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentAnimalCount = MutableLiveData(0)
    val currentAnimalCount: LiveData<Int> get() = _currentAnimalCount

     lateinit var currentAnimal: String
     lateinit var secondAnimal: String

    init {
        getNextAnimal()
    }

    fun reinitialiseData() {
        _score.value = 0
        _currentAnimalCount.value = 0
        getNextAnimal()
    }

    // This is the old way
    private fun getNextAnimal() {
        currentAnimal = allAnimals.random()
        secondAnimal = allAnimals.random()

        if (secondAnimal == currentAnimal) {
            secondAnimal = allAnimals.random()
        }
    }

    private fun increaseScore() {
        _score.value = (score.value)?.plus(SCORE_INCREASE)
    }


    fun nextAnimal(): Boolean {
        return if (currentAnimalCount.value!! < MAX_NO_OF_ANIMALS) {
            getNextAnimal()
            true
        } else false
    }
}

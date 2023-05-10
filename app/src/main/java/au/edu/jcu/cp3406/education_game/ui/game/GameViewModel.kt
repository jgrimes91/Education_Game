package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentAnimalCount = MutableLiveData(0)
    val currentAnimalCount: LiveData<Int> get() = _currentAnimalCount

    private lateinit var currentAnimal: String
    private lateinit var secondAnimal: String

    init {
        getNextAnimal()
    }

    private fun getNextAnimal() {
        currentAnimal = allAnimals.random()
        secondAnimal = allAnimals.random()

        if (secondAnimal == currentAnimal){
            secondAnimal = allAnimals.random()
        }
    }

    private fun increaseScore(){
        _score.value = (score.value)?.plus(SCORE_INCREASE)
    }

}

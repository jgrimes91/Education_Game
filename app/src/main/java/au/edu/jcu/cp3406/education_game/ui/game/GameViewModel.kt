package au.edu.jcu.cp3406.education_game.ui.game

import android.media.MediaPlayer
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

    private val _currentAnimalModified = MutableLiveData<String>()
    val currentAnimalModified: MutableLiveData<String> get() = _currentAnimalModified

    private lateinit var currentAnimal: String

    private lateinit var animalSound: MediaPlayer

    init {
        getNextAnimal()
    }

    fun resetData(){
        _score.value = 0
        _currentAnimalCount.value = 0
        animalList.clear()
    }

    fun checkGuess(guess: String): Boolean {
        if (guess.equals(currentAnimal, true)) {
            increaseScore()
            return true
        } else {
            _score.value = score.value
            return false
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
            getAnimalResource()
//            playAnimalSound()
        }
    }

//    private fun playAnimalSound() {
//        if(!this::animalSound.isInitialized){
//
//        }
//    }

    /**
     * Links animal string to correct resource files
     */
    private fun getAnimalResource() {
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
            false
        }
    }
}

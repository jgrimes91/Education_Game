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


    private val animalsImages = arrayOf(R.drawable.cow, R.drawable.chicken, R.drawable.pig)
    val randomAnimalImage: Int
        get() {
            return animalsImages.random()
        }
//    var currentAnimal
//    lateinit var secondAnimal: String

    init {
//        getNextAnimal()
    }

//    // This is the old way
//    fun getNextAnimal(): String {
//        currentAnimal = allAnimals.random()
////        secondAnimal = allAnimals.random()
////
////        if (secondAnimal == currentAnimal) {
////            secondAnimal = allAnimals.random()
////        }
//        return currentAnimal
//    }

//    private fun increaseScore() {
//        _score.value = (score.value)?.plus(SCORE_INCREASE)
//    }


//    fun nextAnimal(): Boolean {
//        return if (currentAnimalCount.value!! < MAX_NO_OF_ANIMALS) {
//            getNextAnimal()
//            true
//        } else false
//    }
}

package au.edu.jcu.cp3406.education_game.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var score = 0

    private var animals: MutableList<String> = mutableListOf()
    private var _currentAnimal = "test"
    val currentAnimal: String get() = _currentAnimal
    private lateinit var secondAnimal: String

    init {
        getNextAnimal()
    }

    private fun getNextAnimal() {
//        firstAnimal = allAnimals.random()
//        secondAnimal = allAnimals.random()
//
//        while (secondAnimal == firstAnimal){
//            secondAnimal = allAnimals.random()
//        }

    }
}

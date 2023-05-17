package au.edu.jcu.cp3406.education_game.ui.game

import android.app.Application
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding
    private lateinit var application: Application

    private lateinit var animalSound: MediaPlayer

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private val sensorAdapter = object : SensorAdapter() {
        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            val data = sensorEvent?.values ?: return

            // Checking x-value threshold
            val x = data[0]

            if (x > 30) {
                playAnimalSound()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao
        val viewModelFactory = GameViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        val gameViewModel =
            (ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java))
        binding.gameViewModel = gameViewModel

        // Initialise Sensor Manager
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER)

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        binding.lifecycleOwner = this

        binding.maxAnimalCount = MAX_NO_OF_ANIMALS

        viewModel.drawableResId.observe(this) { drawableResId ->
            binding.currentAnimal.setImageResource(drawableResId)
        }

        binding.guessButton.setOnClickListener { guessAnimal() }

        sensorManager.registerListener(
            sensorAdapter,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorAdapter)
    }

    /**
     * Not sure if this part is in the right section or correct??
     */
    private fun playAnimalSound() {
        val soundResId = viewModel.getAnimalSoundResId(viewModel.currentAnimal)

        if (!this::animalSound.isInitialized) {
            animalSound = MediaPlayer.create(application, soundResId)
        }
        if (animalSound.isPlaying) {
            animalSound.pause()
            animalSound.seekTo(0)
        }
        animalSound.start()
    }


    private fun guessAnimal() {
        animalSound.stop()
        val guess = binding.guessInputEditText.text.toString()

        viewModel.checkGuess(guess)
        if (!viewModel.nextAnimal()) {
            showFinalScore()
        }
    }

    private fun showFinalScore() {
        saveToDatabase()

        var titleMessage = ""

        if (viewModel.score.value!! >= 20) {
            titleMessage = "Nice try!"
        } else if (viewModel.score.value!! == 30) {
            titleMessage = "Well done!"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleMessage)
            .setMessage("You scored ${viewModel.score.value} points")
            .setCancelable(false)
            .setNegativeButton("Back to home") { _, _ ->
                returnToTitle()
            }
            .setPositiveButton("Play again") { _, _ ->
                viewModel.resetData()
            }.show()
    }

    private fun saveToDatabase() {
        TODO("Not yet implemented")
    }

    private fun returnToTitle() {
        findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
    }
}
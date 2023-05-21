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
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var binding: FragmentGameBinding
    private lateinit var application: Application

    private var animalSound: MediaPlayer? = null

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var database: PlayerDatabase
    private lateinit var playerDatabaseDao: PlayerDatabaseDao


    // Initialise sensor adaptor
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
        val viewModelFactory = GameViewModelFactory(dataSource)

        // Get a reference to the ViewModel associated with this fragment
        val gameViewModel =
            (ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java])
        binding.gameViewModel = gameViewModel

        // Initialise Sensor Manager
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER)

        viewModel.resetData()

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        binding.lifecycleOwner = this

        binding.maxAnimalCount = MAX_NO_OF_ANIMALS

        viewModel.drawableResId.observe(this) { drawableResId ->
            binding.currentAnimal.setImageResource(drawableResId)
        }

        binding.guessButton.setOnClickListener {
            guessAnimal()
        }

        sensorManager.registerListener(
            sensorAdapter,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        database = PlayerDatabase.getInstance(requireContext())
        playerDatabaseDao = database.playerDatabaseDao

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorAdapter)
    }

    override fun onDestroy() {
        animalSound?.release()
        animalSound?.stop()
        super.onDestroy()
    }

    /**
     * Gets the current animal from GameViewModel and plays the noise.
     */
    private fun playAnimalSound() {
        animalSound.apply {
            if (animalSound?.isPlaying == true) {
                animalSound?.stop()
            }
            animalSound?.reset()
            animalSound?.release()

            animalSound = MediaPlayer.create(application, viewModel.getAnimalSoundResId())
            animalSound?.start()
        }
    }

    /**
     * Checks the user input against the current animal.
     */
    private fun guessAnimal() {
        val guess = binding.guessInputEditText.text.toString()

        viewModel.checkGuess(guess)
        if (!viewModel.nextAnimal()) {
            endOfGameDialogue()
        }
    }


    /**
     * Displays dialogue box on completion of game. Title is customised depending on user performance.
     */
    private fun endOfGameDialogue() {
        var titleMessage = ""
        if (viewModel.score.value!! <= 20) {
            titleMessage = "Nice try!"
        } else if (viewModel.score.value!! == 30) {
            titleMessage = "Well done!"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleMessage)
            .setMessage("You scored ${viewModel.score.value} points")
            .setCancelable(false)
            .setNegativeButton("Share to social media?") { _, _ ->
                shareStats()
            }
            .setPositiveButton("Back to home") { _, _ ->
                returnToTitle()
            }.show()
    }

    /**
     * Share stats to social media
     */
    private fun shareStats() {
        returnToTitle() // Did not implement yet, returns to title so app doesn't crash. Permissions set up in manifest file.
    }

    private fun returnToTitle() {
        findNavController().navigate(R.id.action_gameFragment_to_titleFragment)
    }
}
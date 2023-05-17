package au.edu.jcu.cp3406.education_game.ui.game

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

open class SensorAdapter : SensorEventListener {
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracyValue: Int) {
    }
}
package au.edu.jcu.cp3406.education_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    var difficulty: Int = 0

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun changeDifficulty() {
        difficulty = when (binding.difficultySelection.checkedRadioButtonId) {
            R.id.easyMode -> 1
            R.id.normalMode -> 2
            else -> 3
        }
    }

}
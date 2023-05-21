package au.edu.jcu.cp3406.education_game.ui.game

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import au.edu.jcu.cp3406.education_game.database.Player

@BindingAdapter("src")
fun ImageView.setSrc(resource: MutableLiveData<Int>?){
    resource?.value.let {
        if (it != null) {
            setImageResource(it)
        }
    }
}

@BindingAdapter("difficultyFormatted")
fun TextView.formatDifficulty(item: Player){
    text = when (item.difficulty){
        1 -> "Easy"
        2 -> "Normal"
        else -> "Hard"
    }
}

@BindingAdapter("playerNameFormatted")
fun TextView.formatPlayerName(item:Player){
    text = item.name
}

@BindingAdapter("playerScoreFormatted")
fun TextView.formatScore(item:Player){
    text = item.score.toString()
}
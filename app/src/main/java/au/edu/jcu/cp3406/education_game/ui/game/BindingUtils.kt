package au.edu.jcu.cp3406.education_game.ui.game

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

@BindingAdapter("src")
fun ImageView.setSrc(resource: MutableLiveData<Int>?){
    resource?.value.let {
        if (it != null) {
            setImageResource(it)
        }
    }
}


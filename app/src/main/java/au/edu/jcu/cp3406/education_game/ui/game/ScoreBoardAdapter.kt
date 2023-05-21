package au.edu.jcu.cp3406.education_game.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import au.edu.jcu.cp3406.education_game.database.Player
import au.edu.jcu.cp3406.educationgame.databinding.ListItemPlayerBinding

class ScoreBoardAdapter :
    ListAdapter<Player, ScoreBoardAdapter.PlayerViewHolder>(ScoreBoardDiffCallback()) {

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.from(parent)
    }

    class PlayerViewHolder private constructor(val binding: ListItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Player) {
            binding.player = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlayerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPlayerBinding.inflate(layoutInflater, parent, false)
                return PlayerViewHolder(binding)
            }
        }
    }
}

class ScoreBoardDiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.playerId == newItem.playerId
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}

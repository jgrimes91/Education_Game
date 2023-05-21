package au.edu.jcu.cp3406.education_game.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.educationgame.R
import au.edu.jcu.cp3406.educationgame.databinding.FragmentScoreBoardBinding
import kotlinx.coroutines.launch


class ScoreBoardFragment : Fragment() {
    private lateinit var binding: FragmentScoreBoardBinding
    private lateinit var database: PlayerDatabase
    private lateinit var adapter: ScoreBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_board, container, false)

        adapter = ScoreBoardAdapter()

        binding.playerList.adapter = adapter
        binding.playerList.layoutManager = LinearLayoutManager(requireContext())

        database = PlayerDatabase.getInstance(requireContext())
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadPlayers()
        binding.clearScoreBoard.setOnClickListener { clearScore() }
    }


    /**
     * Clears PlayerDatabse
     */
    private fun clearScore() {
        lifecycleScope.launch {
            database.playerDatabaseDao.clearPlayers()
        }
    }


    /**
     * Gets all the players from PlayerDatabase and displays their stats
     */
    private fun loadPlayers() {
        database.playerDatabaseDao.getAllPlayers().observe(viewLifecycleOwner){players->
            adapter.submitList(players)
        }
    }
}
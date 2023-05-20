package au.edu.jcu.cp3406.education_game.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDatabaseDao {

    @Insert
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Query("DELETE FROM player_table")
    suspend fun clearPlayers()

    @Query("SELECT * FROM player_table ORDER BY score DESC")
    fun getAllPlayers(): LiveData<Player>
    suspend fun getPlayer(): Player

//    @Query("SELECT * from high_score_table where playerId =: playerId")
//    suspend fun getPlayer(playerId: Long): Player?
}
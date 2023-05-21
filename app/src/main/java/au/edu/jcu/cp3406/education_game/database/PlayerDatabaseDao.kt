package au.edu.jcu.cp3406.education_game.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface PlayerDatabaseDao {

    @Insert
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Query("DELETE FROM player_table")
    suspend fun clearPlayers()

    @Query("SELECT * FROM player_table WHERE playerId = :key")
    suspend fun get(key: Long): Player

    @Query("SELECT * FROM player_table ORDER BY score DESC")
    fun getAllPlayers(): List<Player>

    @Query("SELECT * FROM player_table LIMIT 1")
    suspend fun getPlayer(): Player


}
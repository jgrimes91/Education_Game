package au.edu.jcu.cp3406.education_game.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScoreboardDatabaseDao {

    @Insert
    suspend fun insert(score: Scoreboard)

    @Update
    suspend fun update(score: Scoreboard)

    @Query("DELETE FROM high_score_table")
    suspend fun clear()

    @Query("SELECT * FROM high_score_table ORDER BY score DESC")
    fun getAllScores(): LiveData<Scoreboard>
}
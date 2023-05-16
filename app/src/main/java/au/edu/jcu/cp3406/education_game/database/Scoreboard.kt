package au.edu.jcu.cp3406.education_game.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "high_score_table")
data class Scoreboard(
    @PrimaryKey(autoGenerate = true)
    var playerId: Long = 0L,

    @ColumnInfo(name = "player_name")
    var playerName: String,

    @ColumnInfo(name = "score")
    var score: Int = 0
)
package au.edu.jcu.cp3406.education_game.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Scoreboard::class], version = 1, exportSchema = false)
abstract class ScoreBoardDatabase : RoomDatabase(){
    /**
     * Connects the database to the DAO
     */
    abstract val scoreboardDatabaseDao: ScoreboardDatabaseDao

    /**
     * Companion object allows us to add functions on the Scoreboard class.
     */
    companion object {

        /**
         * INSTANCE will keep a reference to any database via getInstance.
         *
         * This will help avoid repeatedly initialising the database.
         *
         * The volatile variable will never be cached, and all writes and reads
         * will be done to and from the main memory. It means that changes made by one thread
         * to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: ScoreBoardDatabase? = null

        fun getInstance(context: Context): ScoreBoardDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ScoreBoardDatabase::class.java,
                        "score_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
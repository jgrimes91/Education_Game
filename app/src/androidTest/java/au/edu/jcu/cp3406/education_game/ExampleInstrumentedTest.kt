package au.edu.jcu.cp3406.education_game

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import au.edu.jcu.cp3406.education_game.database.Player
import au.edu.jcu.cp3406.education_game.database.PlayerDatabase
import au.edu.jcu.cp3406.education_game.database.PlayerDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun insert_Player_load_player() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val database: PlayerDatabase = PlayerDatabase.getInstance(context)
        val playerDatabaseDao: PlayerDatabaseDao = database.playerDatabaseDao

        playerDatabaseDao.clearPlayers()

        val player = Player(name = "Jess", difficulty = 3, score = 10)
        playerDatabaseDao.insertPlayer(player)

        val loadedPlayer = playerDatabaseDao.getPlayer()
        assertEquals("Jess",loadedPlayer.name)
        assertEquals(3, loadedPlayer.difficulty)
        assertEquals(10, loadedPlayer.score)
    }
}
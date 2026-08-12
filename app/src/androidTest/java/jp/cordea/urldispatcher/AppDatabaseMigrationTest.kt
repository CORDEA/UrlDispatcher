package jp.cordea.urldispatcher

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseMigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            AppDatabase::class.java,
            emptyList(),
            FrameworkSQLiteOpenHelperFactory()
    )

    @After
    fun tearDown() {
        ApplicationProvider.getApplicationContext<android.content.Context>()
                .deleteDatabase(TEST_DB)
    }

    @Test
    fun migrate1To2_preservesRowsAndDefaultsDispatchType() {
        helper.createDatabase(TEST_DB, 1).use { db ->
            db.execSQL(
                    """
                    INSERT INTO Url (id, url, description, addedAt)
                    VALUES (1, 'https://example.com', 'universal link', 1700000000000)
                    """.trimIndent()
            )
        }

        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2).close()

        val database = Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java,
                TEST_DB
        )
                .addMigrations(MIGRATION_1_2)
                .build()

        val urls = runBlocking { database.urlDao().getUrls().first() }
        database.close()

        assertThat(urls).hasSize(1)
        val row = urls.single()
        assertThat(row.id).isEqualTo(1L)
        assertThat(row.url).isEqualTo("https://example.com")
        assertThat(row.description).isEqualTo("universal link")
        assertThat(row.addedAt).isEqualTo(1700000000000L)
        assertThat(row.dispatchType).isEqualTo(DispatchType.DEFAULT)
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}

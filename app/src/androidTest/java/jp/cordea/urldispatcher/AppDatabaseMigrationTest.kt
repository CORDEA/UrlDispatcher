package jp.cordea.urldispatcher

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import android.database.sqlite.SQLiteConstraintException
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

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

    @Test
    fun migrate1To2_allowsSameUrlWithDifferentDispatchType() {
        helper.createDatabase(TEST_DB, 1).use { db ->
            db.execSQL(
                    """
                    INSERT INTO Url (id, url, description, addedAt)
                    VALUES (1, 'https://example.com', 'default', 1700000000000)
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

        try {
            runBlocking {
                database.urlDao().insertUrl(
                        Url(
                                id = 0,
                                url = "https://example.com",
                                description = "chooser",
                                addedAt = Date().time,
                                dispatchType = DispatchType.CHOOSER
                        )
                )
            }

            val urls = runBlocking { database.urlDao().getUrls().first() }
            assertThat(urls.map { it.dispatchType })
                    .containsExactly(DispatchType.DEFAULT, DispatchType.CHOOSER)
        } finally {
            database.close()
        }
    }

    @Test
    fun migrate1To2_rejectsDuplicateUrlAndDispatchType() {
        helper.createDatabase(TEST_DB, 1).use { db ->
            db.execSQL(
                    """
                    INSERT INTO Url (id, url, description, addedAt)
                    VALUES (1, 'https://example.com', 'default', 1700000000000)
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

        try {
            assertThrows(SQLiteConstraintException::class.java) {
                runBlocking {
                    database.openHelper.writableDatabase.execSQL(
                            """
                            INSERT INTO Url (url, description, addedAt, dispatch_type)
                            VALUES ('https://example.com', 'dup', 1700000000001, 0)
                            """.trimIndent()
                    )
                }
            }
        } finally {
            database.close()
        }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}

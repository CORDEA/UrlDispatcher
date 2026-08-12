package jp.cordea.urldispatcher

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Url::class], version = 2)
@TypeConverters(DispatchTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun urlDao(): UrlDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE `Url` ADD COLUMN `dispatch_type` INTEGER NOT NULL DEFAULT 0")
    }
}

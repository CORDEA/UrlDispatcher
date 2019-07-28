package jp.cordea.urldispatcher

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val testModule = module {
            single {
                Room.inMemoryDatabaseBuilder(androidContext(), AppDatabase::class.java).build()
            }
        }
        startKoin {
            androidContext(this@TestApp)
            modules(listOf(appModule, testModule))
        }
    }
}

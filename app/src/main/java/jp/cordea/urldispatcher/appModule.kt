package jp.cordea.urldispatcher

import androidx.room.Room
import jp.cordea.urldispatcher.main.MainActivity
import jp.cordea.urldispatcher.main.MainAdapter
import jp.cordea.urldispatcher.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "urlDispatcher").build()
    }
    single { get<AppDatabase>().urlDao() }
    single { UrlLocalDataSource(get()) }
    single<UrlRepository> { UrlRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
    scope(MainActivity.SCOPE) { MainAdapter() }
}

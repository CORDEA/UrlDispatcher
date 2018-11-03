package jp.cordea.urldispatcher

import androidx.room.Room
import jp.cordea.urldispatcher.add.AddViewModel
import jp.cordea.urldispatcher.main.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "urlDispatcher").build()
    }
    single { get<AppDatabase>().urlDao() }
    single { UrlLocalDataSource(get()) }
    single<UrlRepository> { UrlRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }

    scope(MainActivity.SCOPE) { (activity: MainActivity) -> MainNavigator(activity) }
    scope(MainActivity.SCOPE) { (activity: MainActivity) ->
        MainListItemProvider(get { parametersOf(activity) })
    }
    scope(MainActivity.SCOPE) { (activity: MainActivity) ->
        MainAdapter(get { parametersOf(activity) })
    }

    viewModel { AddViewModel(get()) }
}

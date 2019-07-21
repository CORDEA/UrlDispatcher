package jp.cordea.urldispatcher

import androidx.room.Room
import jp.cordea.urldispatcher.add.AddViewModel
import jp.cordea.urldispatcher.main.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "urlDispatcher").build()
    }
    single { get<AppDatabase>().urlDao() }
    single { UrlLocalDataSource(get()) }
    single<UrlRepository> { UrlRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }

    scope(named<MainFragment>()) {
        scoped { (fragment: MainFragment) -> MainNavigator(fragment) }
        scoped { (fragment: MainFragment) ->
            MainListItemProvider(get { parametersOf(fragment) })
        }
        scoped { (fragment: MainFragment) ->
            MainAdapter(get { parametersOf(fragment) })
        }
    }

    viewModel { AddViewModel(get()) }
}

package jp.cordea.urldispatcher

import androidx.room.Room
import jp.cordea.urldispatcher.add.AddFragment
import jp.cordea.urldispatcher.add.AddNavigator
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

    viewModel { MainViewModel() }

    viewModel { HomeViewModel(get()) }

    scope(named<HomeFragment>()) {
        scoped { (fragment: HomeFragment) -> HomeNavigator(fragment) }
        scoped { (fragment: HomeFragment) ->
            HomeListItemProvider(get { parametersOf(fragment) })
        }
        scoped { (fragment: HomeFragment) ->
            HomeAdapter(get { parametersOf(fragment) })
        }
    }

    viewModel { HomeBottomSheetViewModel(get()) }

    scope(named<HomeBottomSheetDialogFragment>()) {
        scoped { (fragment: HomeBottomSheetDialogFragment) -> HomeBottomSheetNavigator(fragment) }
    }

    viewModel { AddViewModel(get()) }

    scope(named<AddFragment>()) {
        scoped { (fragment: AddFragment) -> AddNavigator(fragment) }
    }
}

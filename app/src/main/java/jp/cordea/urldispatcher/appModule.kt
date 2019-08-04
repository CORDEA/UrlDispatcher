package jp.cordea.urldispatcher

import jp.cordea.urldispatcher.edit.EditFragment
import jp.cordea.urldispatcher.edit.EditNavigator
import jp.cordea.urldispatcher.edit.EditViewModel
import jp.cordea.urldispatcher.home.*
import jp.cordea.urldispatcher.licenses.LicenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { get<AppDatabase>().urlDao() }
    single { UrlLocalDataSource(get()) }
    single<UrlRepository> { UrlRepositoryImpl(get()) }

    single { LicenseLocalDataSource(get()) }
    single<LicenseRepository> { LicenseRepositoryImpl(get())}

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

    viewModel { EditViewModel(get()) }

    scope(named<EditFragment>()) {
        scoped { (fragment: EditFragment) -> EditNavigator(fragment) }
    }

    viewModel { LicenseViewModel(get()) }
}

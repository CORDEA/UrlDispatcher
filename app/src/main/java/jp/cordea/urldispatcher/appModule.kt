package jp.cordea.urldispatcher

import jp.cordea.urldispatcher.ui.edit.EditViewModel
import jp.cordea.urldispatcher.ui.home.HomeViewModel
import jp.cordea.urldispatcher.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { get<AppDatabase>().urlDao() }
    single { UrlLocalDataSource(get()) }
    single<UrlRepository> { UrlRepositoryImpl(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { (id: Long) -> EditViewModel(get(), id) }
    viewModel { SettingsViewModel() }
}

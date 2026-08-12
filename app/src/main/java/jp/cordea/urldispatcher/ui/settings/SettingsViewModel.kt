package jp.cordea.urldispatcher.ui.settings

import androidx.lifecycle.ViewModel
import jp.cordea.urldispatcher.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
            SettingsUiState(
                    licenseCount = BuildConfig.OSS_LICENSE_COUNT,
                    versionName = BuildConfig.VERSION_NAME,
                    versionCode = BuildConfig.VERSION_CODE
            )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
}

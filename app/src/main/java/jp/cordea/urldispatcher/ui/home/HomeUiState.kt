package jp.cordea.urldispatcher.ui.home

import android.net.Uri
import jp.cordea.urldispatcher.DispatchType

data class HomeUiState(
        val isLoading: Boolean = true,
        val items: List<HomeLinkItem> = emptyList(),
        val schemes: List<String> = emptyList(),
        val selectedScheme: String? = null,
        val totalCount: Int = 0,
        val query: String = "",
        val isSearchActive: Boolean = false
) {
    val isFiltered: Boolean
        get() = selectedScheme != null || query.isNotBlank()
}

data class HomeLinkItem(
        val id: Long,
        val scheme: String,
        val url: String,
        val description: String,
        val date: String,
        val uri: Uri,
        val dispatchType: DispatchType
)

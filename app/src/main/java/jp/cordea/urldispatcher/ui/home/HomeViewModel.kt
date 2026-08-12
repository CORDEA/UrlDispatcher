package jp.cordea.urldispatcher.ui.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed interface HomeEvent {
    data class OpenLink(val item: HomeLinkItem) : HomeEvent
}

class HomeViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val selectedSchemeFlow = MutableStateFlow<String?>(null)

    private val eventsChannel = Channel<HomeEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    val uiState: StateFlow<HomeUiState> = combine(
            repository.getUrls().catch { emit(emptyList()) },
            selectedSchemeFlow
    ) { urls, selected ->
        val items = urls.map { it.toItem() }
        val schemes = items.map { it.scheme }.distinct().sorted()
        val resolvedSelection = selected?.takeIf { it in schemes }
        val filtered = if (resolvedSelection == null) items
        else items.filter { it.scheme == resolvedSelection }
        HomeUiState(
                isLoading = false,
                items = filtered,
                schemes = schemes,
                selectedScheme = resolvedSelection,
                totalCount = items.size
        )
    }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
    )

    fun selectScheme(scheme: String?) {
        selectedSchemeFlow.value = scheme
    }

    fun onItemClick(item: HomeLinkItem) {
        viewModelScope.launch { eventsChannel.send(HomeEvent.OpenLink(item)) }
    }

    fun deleteLink(id: Long) {
        viewModelScope.launch {
            runCatching { repository.deleteUrl(id) }
        }
    }
}

private val DATE_FORMAT = SimpleDateFormat("MMM d, HH:mm", Locale.getDefault())

internal fun Url.toItem(): HomeLinkItem {
    val uri = Uri.parse(url)
    val scheme = uri.scheme?.lowercase(Locale.ROOT) ?: "custom"
    return HomeLinkItem(
            id = id,
            scheme = scheme,
            url = url,
            description = description,
            date = DATE_FORMAT.format(Date(addedAt)),
            uri = uri,
            dispatchType = dispatchType
    )
}

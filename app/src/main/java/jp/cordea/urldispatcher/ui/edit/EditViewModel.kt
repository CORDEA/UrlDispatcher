package jp.cordea.urldispatcher.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class EditViewModel(
        private val repository: UrlRepository,
        private val id: Long
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState(isEditMode = id > 0L))
    val uiState: StateFlow<EditUiState> = _uiState.asStateFlow()

    private val eventsChannel = Channel<EditEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    init {
        if (id > 0L) {
            _uiState.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                val existing = runCatching { repository.findUrl(id) }.getOrNull()
                _uiState.update {
                    if (existing == null) {
                        it.copy(isLoading = false)
                    } else {
                        it.copy(
                                isLoading = false,
                                url = existing.url,
                                description = existing.description,
                                dispatchType = existing.dispatchType
                        )
                    }
                }
            }
        }
    }

    fun onUrlChange(value: String) {
        _uiState.update { it.copy(url = value) }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun onDispatchTypeChange(value: DispatchType) {
        _uiState.update { it.copy(dispatchType = value) }
    }

    fun save() {
        val current = _uiState.value
        if (current.url.isBlank()) {
            viewModelScope.launch { eventsChannel.send(EditEvent.Error(EditError.EMPTY_URL)) }
            return
        }
        if (current.isSaving) return
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val result = runCatching {
                repository.insertUrl(
                        Url(
                                id = id,
                                url = current.url,
                                description = current.description,
                                addedAt = Date().time,
                                dispatchType = current.dispatchType
                        )
                )
            }
            _uiState.update { it.copy(isSaving = false) }
            result.fold(
                    onSuccess = { eventsChannel.send(EditEvent.Saved) },
                    onFailure = { eventsChannel.send(EditEvent.Error(EditError.SAVE_FAILED)) }
            )
        }
    }
}

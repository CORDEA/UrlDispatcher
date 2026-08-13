package jp.cordea.urldispatcher.ui.edit

import jp.cordea.urldispatcher.DispatchType

data class EditUiState(
        val isEditMode: Boolean = false,
        val isLoading: Boolean = false,
        val isSaving: Boolean = false,
        val url: String = "",
        val description: String = "",
        val dispatchType: DispatchType = DispatchType.DEFAULT
)

sealed interface EditEvent {
    data object Saved : EditEvent
    data class Error(val kind: EditError) : EditEvent
}

enum class EditError {
    EMPTY_URL,
    DUPLICATE,
    SAVE_FAILED
}

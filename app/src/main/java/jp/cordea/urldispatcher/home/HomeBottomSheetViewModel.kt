package jp.cordea.urldispatcher.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.launch

class HomeBottomSheetViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _dismiss = MutableLiveData<Unit>()
    val dismiss: LiveData<Unit> = _dismiss

    private val _showEditor = MutableLiveData<Long>()
    val showEditor: LiveData<Long> = _showEditor

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> = _error

    private var id: Long = 0L

    fun init(id: Long) {
        this.id = id
    }

    fun edit() {
        _showEditor.value = id
        _dismiss.value = Unit
    }

    fun delete() {
        viewModelScope.launch {
            try {
                repository.deleteUrl(id)
                _dismiss.value = Unit
            } catch (e: Exception) {
                _error.value = ErrorType.UNKNOWN
            }
        }
    }

    enum class ErrorType {
        UNKNOWN
    }
}

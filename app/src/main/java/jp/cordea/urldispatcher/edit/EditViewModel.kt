package jp.cordea.urldispatcher.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.launch
import java.util.Date

class EditViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> = _error

    private val _popBackStack = MutableLiveData<Unit>()
    val popBackStack: LiveData<Unit> = _popBackStack

    val url = ObservableField<String>()
    val description = ObservableField<String>()

    private var id: Long = 0L

    fun init(id: Long) {
        this.id = id
        if (id <= 0L) {
            return
        }
        viewModelScope.launch {
            repository.findUrl(id)?.let {
                url.set(it.url)
                description.set(it.description)
            }
        }
    }

    fun trySaveUrl() {
        val url = url.get()
        val description = description.get()
        if (url.isNullOrBlank()) {
            _error.value = ErrorType.EMPTY_URL
            return
        }
        viewModelScope.launch {
            try {
                repository.insertUrl(Url(id, url, description ?: "", Date().time))
                _popBackStack.value = Unit
            } catch (e: Exception) {
                _error.value = ErrorType.UNKNOWN
            }
        }
    }

    enum class ErrorType {
        EMPTY_URL,
        UNKNOWN
    }
}

package jp.cordea.urldispatcher.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.UrlRepository

class HomeBottomSheetViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _dismiss = MutableLiveData<Unit>()
    val dismiss: LiveData<Unit> = _dismiss

    private val _showEditor = MutableLiveData<Long>()
    val showEditor: LiveData<Long> = _showEditor

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> = _error

    private var disposable: Disposable? = null
    private var id: Long = 0L

    fun init(id: Long) {
        this.id = id
    }

    fun edit() {
        _showEditor.value = id
        _dismiss.value = Unit
    }

    fun delete() {
        disposable = repository.deleteUrl(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { _dismiss.value = Unit },
                        onError = { _error.value = ErrorType.UNKNOWN }
                )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    enum class ErrorType {
        UNKNOWN
    }
}

package jp.cordea.urldispatcher.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import java.util.*

class EditViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _url = MutableLiveData<Url>()
    val url: LiveData<Url> = _url

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> = _error

    private val _popBackStack = MutableLiveData<Unit>()
    val popBackStack: LiveData<Unit> = _popBackStack

    fun init(url: String?) {
        url ?: return
        repository.findUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { _url.value = it }
                .addTo(compositeDisposable)
    }

    fun trySaveUrl(url: String?, description: String?) {
        if (url.isNullOrBlank()) {
            _error.value = ErrorType.EMPTY_URL
            return
        }
        repository.insertUrl(Url(url, description ?: "", Date().time))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { _popBackStack.value = Unit },
                        onError = { _error.value = ErrorType.UNKNOWN }
                )
                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    enum class ErrorType {
        EMPTY_URL,
        UNKNOWN
    }
}

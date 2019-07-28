package jp.cordea.urldispatcher.main

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

    private var disposable: Disposable? = null
    private lateinit var url: String

    fun init(url: String) {
        this.url = url
    }

    fun delete() {
        disposable = repository.deleteUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { _dismiss.value = Unit }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

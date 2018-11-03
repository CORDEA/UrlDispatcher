package jp.cordea.urldispatcher.add

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import java.util.*

class AddViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private var disposable: Disposable? = null

    fun storeUrl(url: String, description: String) {
        disposable = repository.insertUrl(Url(url, description, Date().time))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

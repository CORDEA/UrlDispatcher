package jp.cordea.urldispatcher.add

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import java.util.*

class AddViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _url = BehaviorProcessor.create<Url>()
    val url: Flowable<Url> = _url.observeOn(AndroidSchedulers.mainThread())

    fun init(url: String?) {
        url ?: return
        repository.findUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { _url.onNext(it) }
                .addTo(compositeDisposable)
    }

    fun storeUrl(url: String, description: String) {
        repository.insertUrl(Url(url, description, Date().time))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { }
                .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

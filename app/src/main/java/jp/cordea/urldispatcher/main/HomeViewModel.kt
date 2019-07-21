package jp.cordea.urldispatcher.main

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import jp.cordea.urldispatcher.UrlRepository

class HomeViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _adapterItems = BehaviorProcessor.create<List<HomeListItemModel>>()
    val adapterItems: Flowable<List<HomeListItemModel>> =
            _adapterItems.observeOn(AndroidSchedulers.mainThread())

    private var disposable: Disposable? = null

    fun refresh() {
        disposable = repository.getUrls()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    _adapterItems.onNext(list.map { HomeListItemModel.from(it) })
                }, {
                    _adapterItems.onNext(emptyList())
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

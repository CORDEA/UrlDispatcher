package jp.cordea.urldispatcher.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import jp.cordea.urldispatcher.UrlRepository

class HomeViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _adapterItems = MutableLiveData<List<HomeListItemModel>>()
    val adapterItems: LiveData<List<HomeListItemModel>> = _adapterItems

    private var disposable: Disposable? = null

    fun refresh() {
        disposable = repository.getUrls()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    _adapterItems.value = list.map { HomeListItemModel.from(it) }
                }, {
                    _adapterItems.value = emptyList()
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

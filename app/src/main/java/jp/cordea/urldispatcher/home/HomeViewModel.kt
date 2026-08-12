package jp.cordea.urldispatcher.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    private val _adapterItems = MutableLiveData<List<HomeListItemModel>>()
    val adapterItems: LiveData<List<HomeListItemModel>> = _adapterItems

    fun refresh() {
        viewModelScope.launch {
            val list = repository.getUrls()
                    .catch { _adapterItems.postValue(emptyList()) }
                    .first()
            _adapterItems.value = list.map { HomeListItemModel.from(it) }
        }
    }
}

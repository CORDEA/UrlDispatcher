package jp.cordea.urldispatcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _refresh = MutableLiveData<Unit>()
    val refresh: LiveData<Unit> = _refresh

    fun requestUpdate() {
        _refresh.value = Unit
    }
}

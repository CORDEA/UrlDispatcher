package jp.cordea.urldispatcher.licenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.LicenseRepository

class LicenseViewModel(
        private val repository: LicenseRepository
) : ViewModel() {
    private val _html = MutableLiveData<String>()
    val html: LiveData<String> = _html

    private val serialDisposable = SerialDisposable()

    fun init() {
        repository.getLicenses()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { _html.value = it }
                .run(serialDisposable::set)
    }

    override fun onCleared() {
        super.onCleared()
        serialDisposable.dispose()
    }
}

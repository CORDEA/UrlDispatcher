package jp.cordea.urldispatcher.main

import androidx.lifecycle.ViewModel
import jp.cordea.urldispatcher.UrlRepository

class MainViewModel(
        private val repository: UrlRepository
) : ViewModel() {
    fun start() {
    }
}

package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe

interface UrlRepository {
    fun insertUrl(url: String): Completable
    fun getUrls(): Maybe<List<Url>>
}

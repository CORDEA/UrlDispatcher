package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe

interface UrlRepository {
    fun insertUrl(url: Url): Completable
    fun findUrl(url: String): Maybe<Url>
    fun getUrls(): Maybe<List<Url>>
    fun deleteUrl(url: String): Completable
}

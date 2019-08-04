package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface UrlRepository {
    fun insertUrl(url: Url): Completable
    fun findUrl(id: Long): Maybe<Url>
    fun getUrls(): Single<List<Url>>
    fun deleteUrl(id: Long): Completable
}

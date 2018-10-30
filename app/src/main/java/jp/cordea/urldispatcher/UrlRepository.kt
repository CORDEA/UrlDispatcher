package jp.cordea.urldispatcher

import io.reactivex.Maybe

interface UrlRepository {
    fun insertUrl(url: Url)
    fun getUrls(): Maybe<List<Url>>
}

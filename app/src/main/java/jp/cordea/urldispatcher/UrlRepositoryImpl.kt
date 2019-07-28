package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class UrlRepositoryImpl(
        private val localDataSource: UrlLocalDataSource
) : UrlRepository {
    override fun insertUrl(url: Url): Completable =
            localDataSource.insertUrl(url).subscribeOn(Schedulers.io())

    override fun findUrl(url: String): Maybe<Url> =
            localDataSource.findUrl(url).subscribeOn(Schedulers.io())

    override fun getUrls(): Maybe<List<Url>> =
            localDataSource.getUrls().subscribeOn(Schedulers.io())
}

package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class UrlRepositoryImpl(
        private val localDataSource: UrlLocalDataSource
) : UrlRepository {
    override fun insertUrl(url: Url): Completable =
            localDataSource.insertUrl(url).subscribeOn(Schedulers.io())

    override fun findUrl(id: Long): Maybe<Url> =
            localDataSource.findUrl(id).subscribeOn(Schedulers.io())

    override fun getUrls(): Maybe<List<Url>> =
            localDataSource.getUrls().subscribeOn(Schedulers.io())

    override fun deleteUrl(id: Long): Completable =
            localDataSource.deleteUrl(id).subscribeOn(Schedulers.io())
}

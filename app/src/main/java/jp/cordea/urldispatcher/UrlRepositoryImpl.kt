package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class UrlRepositoryImpl(
        private val localDataSource: UrlLocalDataSource
) : UrlRepository {
    override fun insertUrl(url: String): Completable =
            localDataSource.insertUrl(url).subscribeOn(Schedulers.io())
    override fun getUrls(): Maybe<List<Url>> =
            localDataSource.getUrls().subscribeOn(Schedulers.io())
}

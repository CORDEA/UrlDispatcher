package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe

class UrlLocalDataSource(
        private val urlDao: UrlDao
) : UrlRepository {
    override fun insertUrl(url: Url): Completable =
            Completable.create {
                urlDao.insertUrl(url)
                it.onComplete()
            }

    override fun getUrls(): Maybe<List<Url>> = urlDao.getUrls()
}

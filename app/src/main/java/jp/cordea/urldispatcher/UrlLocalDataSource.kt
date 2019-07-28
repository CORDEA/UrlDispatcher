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

    override fun findUrl(url: String): Maybe<Url> = urlDao.findUrl(url)

    override fun getUrls(): Maybe<List<Url>> = urlDao.getUrls()

    override fun deleteUrl(url: String): Completable =
            Completable.create {
                urlDao.deleteUrl(url)
                it.onComplete()
            }
}

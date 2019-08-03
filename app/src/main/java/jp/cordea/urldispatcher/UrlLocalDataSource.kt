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

    override fun findUrl(id: Long): Maybe<Url> = urlDao.findUrl(id)

    override fun getUrls(): Maybe<List<Url>> = urlDao.getUrls()

    override fun deleteUrl(id: Long): Completable =
            Completable.create {
                urlDao.deleteUrl(id)
                it.onComplete()
            }
}

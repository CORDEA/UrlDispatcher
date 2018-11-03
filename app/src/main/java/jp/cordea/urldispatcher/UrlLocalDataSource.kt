package jp.cordea.urldispatcher

import io.reactivex.Completable
import io.reactivex.Maybe
import java.util.*

class UrlLocalDataSource(
        private val urlDao: UrlDao
) : UrlRepository {
    override fun insertUrl(url: String): Completable =
            Completable.create {
                urlDao.insertUrl(Url(url, Date().time))
                it.onComplete()
            }

    override fun getUrls(): Maybe<List<Url>> = urlDao.getUrls()
}

package jp.cordea.urldispatcher

import io.reactivex.Maybe

class UrlLocalDataSource(
        private val urlDao: UrlDao
) : UrlRepository{
    override fun insertUrl(url: Url) = urlDao.insertUrl(url)
    override fun getUrls(): Maybe<List<Url>> = urlDao.getUrls()
}

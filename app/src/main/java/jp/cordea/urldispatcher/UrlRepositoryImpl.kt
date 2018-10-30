package jp.cordea.urldispatcher

import io.reactivex.Maybe

class UrlRepositoryImpl(
        private val localDataSource: UrlLocalDataSource
) : UrlRepository {
    override fun insertUrl(url: Url) = localDataSource.insertUrl(url)
    override fun getUrls(): Maybe<List<Url>> = localDataSource.getUrls()
}

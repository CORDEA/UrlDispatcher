package jp.cordea.urldispatcher

import kotlinx.coroutines.flow.Flow

class UrlLocalDataSource(
        private val urlDao: UrlDao
) : UrlRepository {
    override suspend fun insertUrl(url: Url) = urlDao.insertUrl(url)

    override suspend fun findUrl(id: Long): Url? = urlDao.findUrl(id)

    override fun getUrls(): Flow<List<Url>> = urlDao.getUrls()

    override suspend fun deleteUrl(id: Long) = urlDao.deleteUrl(id)
}

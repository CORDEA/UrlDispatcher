package jp.cordea.urldispatcher

import kotlinx.coroutines.flow.Flow

class UrlRepositoryImpl(
        private val localDataSource: UrlLocalDataSource
) : UrlRepository {
    override suspend fun insertUrl(url: Url) = localDataSource.insertUrl(url)

    override suspend fun updateUrl(url: Url) = localDataSource.updateUrl(url)

    override suspend fun findUrl(id: Long): Url? = localDataSource.findUrl(id)

    override fun getUrls(): Flow<List<Url>> = localDataSource.getUrls()

    override suspend fun deleteUrl(id: Long) = localDataSource.deleteUrl(id)
}

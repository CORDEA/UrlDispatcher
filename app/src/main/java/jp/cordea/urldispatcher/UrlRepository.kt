package jp.cordea.urldispatcher

import kotlinx.coroutines.flow.Flow

interface UrlRepository {
    suspend fun insertUrl(url: Url)
    suspend fun updateUrl(url: Url)
    suspend fun findUrl(id: Long): Url?
    fun getUrls(): Flow<List<Url>>
    suspend fun deleteUrl(id: Long)
}

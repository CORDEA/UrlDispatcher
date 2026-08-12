package jp.cordea.urldispatcher

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUrl(url: Url)

    @Query("SELECT * FROM url WHERE id = :id LIMIT 1")
    suspend fun findUrl(id: Long): Url?

    @Query("SELECT * FROM url")
    fun getUrls(): Flow<List<Url>>

    @Query("DELETE FROM url WHERE id = :id")
    suspend fun deleteUrl(id: Long)
}

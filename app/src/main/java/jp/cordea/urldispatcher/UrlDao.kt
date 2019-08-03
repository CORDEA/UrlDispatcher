package jp.cordea.urldispatcher

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe

@Dao
interface UrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUrl(url: Url)

    @Query("SELECT * FROM url WHERE id = :id LIMIT 1")
    fun findUrl(id: Long): Maybe<Url>

    @Query("SELECT * FROM url")
    fun getUrls(): Maybe<List<Url>>

    @Query("DELETE FROM url WHERE id = :id")
    fun deleteUrl(id: Long)
}

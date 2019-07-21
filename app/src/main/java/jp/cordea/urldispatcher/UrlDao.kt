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

    @Query("SELECT * FROM url WHERE url = :url LIMIT 1")
    fun findUrl(url: String): Maybe<Url>

    @Query("SELECT * FROM url")
    fun getUrls(): Maybe<List<Url>>
}

package jp.cordea.urldispatcher

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUrl(url: Url)

    @Query("SELECT * FROM url")
    fun getUrls(): List<Url>
}

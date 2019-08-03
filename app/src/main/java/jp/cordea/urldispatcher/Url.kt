package jp.cordea.urldispatcher

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["url"], unique = true)])
class Url(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        val url: String,
        val description: String,
        val addedAt: Long
)

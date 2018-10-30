package jp.cordea.urldispatcher

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Url(
        @PrimaryKey val url: String,
        val addedAt: String
)

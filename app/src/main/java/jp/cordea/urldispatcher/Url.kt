package jp.cordea.urldispatcher

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["url", "dispatch_type"], unique = true)])
class Url(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        val url: String,
        val description: String,
        val addedAt: Long,
        @ColumnInfo(name = "dispatch_type", defaultValue = "0")
        val dispatchType: DispatchType = DispatchType.DEFAULT
)

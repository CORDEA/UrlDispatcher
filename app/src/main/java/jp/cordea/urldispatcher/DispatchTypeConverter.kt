package jp.cordea.urldispatcher

import androidx.room.TypeConverter

class DispatchTypeConverter {
    @TypeConverter
    fun fromDispatchType(value: DispatchType): Int = value.value

    @TypeConverter
    fun toDispatchType(value: Int): DispatchType = DispatchType.fromValue(value)
}

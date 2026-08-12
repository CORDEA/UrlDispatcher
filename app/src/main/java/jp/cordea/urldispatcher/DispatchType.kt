package jp.cordea.urldispatcher

enum class DispatchType(val value: Int) {
    DEFAULT(0),
    CHOOSER(1);

    companion object {
        fun fromValue(value: Int): DispatchType =
                entries.firstOrNull { it.value == value } ?: DEFAULT
    }
}

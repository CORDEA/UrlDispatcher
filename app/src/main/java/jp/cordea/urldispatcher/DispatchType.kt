package jp.cordea.urldispatcher

enum class DispatchType(val value: Int) {
    DEFAULT(0),
    BROWSER(1),
    CHOOSER(2);

    companion object {
        fun fromValue(value: Int): DispatchType =
                entries.firstOrNull { it.value == value } ?: DEFAULT
    }
}

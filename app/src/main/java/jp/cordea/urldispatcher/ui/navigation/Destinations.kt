package jp.cordea.urldispatcher.ui.navigation

object Destinations {
    const val HOME = "home"
    const val SETTINGS = "settings"

    const val EDIT_ARG_ID = "id"
    const val EDIT_ROUTE = "edit?$EDIT_ARG_ID={$EDIT_ARG_ID}"

    fun edit(id: Long = 0L) = "edit?$EDIT_ARG_ID=$id"
}

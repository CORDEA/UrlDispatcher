package jp.cordea.urldispatcher.main

class MainListItemProvider(
        private val navigator: MainNavigator
) {
    fun get(model: MainListItemModel) = MainListItem(navigator, model)
}

package jp.cordea.urldispatcher.main

class HomeListItemProvider(
        private val navigator: HomeNavigator
) {
    fun get(model: HomeListItemModel) = HomeListItem(navigator, model)
}

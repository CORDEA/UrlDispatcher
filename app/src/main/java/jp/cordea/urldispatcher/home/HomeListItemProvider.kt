package jp.cordea.urldispatcher.home

class HomeListItemProvider(
        private val navigator: HomeNavigator
) {
    fun get(model: HomeListItemModel) = HomeListItem(navigator, model)
}

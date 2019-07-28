package jp.cordea.urldispatcher.home

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class HomeAdapter(
        private val itemProvider: HomeListItemProvider
) : GroupAdapter<ViewHolder>() {
    fun update(models: List<HomeListItemModel>) {
        clear()
        addAll(models.map { itemProvider.get(it) })
    }
}

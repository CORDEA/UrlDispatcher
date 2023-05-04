package jp.cordea.urldispatcher.home

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class HomeAdapter(
        private val itemProvider: HomeListItemProvider
) : GroupAdapter<GroupieViewHolder>() {
    fun update(models: List<HomeListItemModel>) {
        clear()
        addAll(models.map { itemProvider.get(it) })
    }
}

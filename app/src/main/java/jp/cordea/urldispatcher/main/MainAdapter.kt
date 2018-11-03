package jp.cordea.urldispatcher.main

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MainAdapter(
        private val itemProvider: MainListItemProvider
) : GroupAdapter<ViewHolder>() {
    fun update(models: List<MainListItemModel>) {
        clear()
        addAll(models.map { itemProvider.get(it) })
    }
}

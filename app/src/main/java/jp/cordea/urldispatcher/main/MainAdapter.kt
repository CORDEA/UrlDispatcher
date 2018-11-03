package jp.cordea.urldispatcher.main

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MainAdapter : GroupAdapter<ViewHolder>() {
    fun update(models: List<MainListItemModel>) {
        clear()
        addAll(models.map { MainListItem(it) })
    }
}

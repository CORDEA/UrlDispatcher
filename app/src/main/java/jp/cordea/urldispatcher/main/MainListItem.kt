package jp.cordea.urldispatcher.main

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.databinding.ListItemMainBinding

class MainListItemModel(
        val title: String,
        val description: String,
        val addedAt: String
)

class MainListItem : BindableItem<ListItemMainBinding>() {
    private lateinit var model: MainListItemModel

    fun update(model: MainListItemModel) = apply { this.model = model }

    override fun getLayout(): Int = R.layout.list_item_main

    override fun bind(binding: ListItemMainBinding, position: Int) {
        binding.model = model
    }
}

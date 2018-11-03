package jp.cordea.urldispatcher.main

import com.xwray.groupie.databinding.BindableItem
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.databinding.ListItemMainBinding

class MainListItemModel(
        val title: String,
        val description: String,
        addedAt: Long
) {
    companion object {
        fun from(url: Url) = MainListItemModel(url.url, url.description, url.addedAt)
    }

    val addedAt: String = addedAt.toString()
}

class MainListItem(
        private val navigator: MainNavigator,
        private val model: MainListItemModel
) : BindableItem<ListItemMainBinding>() {
    override fun getLayout(): Int = R.layout.list_item_main

    override fun bind(binding: ListItemMainBinding, position: Int) {
        binding.model = model
        binding.root.setOnClickListener {
            navigator.navigateToWeb()
        }
    }
}

package jp.cordea.urldispatcher.main

import android.net.Uri
import com.xwray.groupie.databinding.BindableItem
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.databinding.HomeListItemBinding

class HomeListItemModel(
        url: String,
        val description: String,
        addedAt: Long
) {
    companion object {
        fun from(url: Url) = HomeListItemModel(url.url, url.description, url.addedAt)
    }

    val title: String = url
    val uri: Uri = Uri.parse(url)
    val addedAt: String = addedAt.toString()
}

class HomeListItem(
        private val navigator: HomeNavigator,
        private val model: HomeListItemModel
) : BindableItem<HomeListItemBinding>() {
    override fun getLayout(): Int = R.layout.home_list_item

    override fun bind(binding: HomeListItemBinding, position: Int) {
        binding.model = model
        binding.root.setOnClickListener {
            navigator.navigateToWeb(model.uri)
        }
    }
}

package jp.cordea.urldispatcher.main

import android.net.Uri
import com.xwray.groupie.databinding.BindableItem
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.databinding.HomeListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeListItemModel(
        url: String,
        val description: String,
        addedAt: Long
) {
    companion object {
        fun from(url: Url) = HomeListItemModel(url.url, url.description, url.addedAt)
    }

    private val formatter = SimpleDateFormat("M/d HH:mm", Locale.getDefault())


    val title: String = url
    val uri: Uri = Uri.parse(url)
    val addedAt: String = formatter.format(Date(addedAt))
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
        binding.root.setOnLongClickListener {
            navigator.showBottomSheet()
            true
        }
    }
}

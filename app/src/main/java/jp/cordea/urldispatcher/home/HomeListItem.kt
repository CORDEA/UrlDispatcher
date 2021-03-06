package jp.cordea.urldispatcher.home

import android.content.ActivityNotFoundException
import android.net.Uri
import com.xwray.groupie.databinding.BindableItem
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.databinding.HomeListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeListItemModel(
        val id: Long,
        url: String,
        val description: String,
        addedAt: Long
) {
    companion object {
        fun from(url: Url) = HomeListItemModel(url.id, url.url, url.description, url.addedAt)
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
            try {
                navigator.navigateToWeb(model.uri)
            } catch (e: ActivityNotFoundException) {
                navigator.showNotFoundErrorToast()
            }
        }
        binding.root.setOnLongClickListener {
            navigator.showBottomSheet(model.id)
            true
        }
    }
}

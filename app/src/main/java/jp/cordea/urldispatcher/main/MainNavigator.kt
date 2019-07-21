package jp.cordea.urldispatcher.main

import android.app.Activity
import android.content.Intent
import android.net.Uri

class MainNavigator(
        private val activity: Activity
) {
    fun navigateToAdd() {
//        activity.startActivityForResult(
//                Intent(activity, AddActivity::class.java),
//                AddActivity.REQUEST_CODE
//        )
    }

    fun navigateToWeb(uri: Uri) {
        activity.startActivity(
                Intent(Intent.ACTION_VIEW, uri)
        )
    }
}

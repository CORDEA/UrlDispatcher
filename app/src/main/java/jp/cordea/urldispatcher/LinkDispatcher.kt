package jp.cordea.urldispatcher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Launch [uri] according to the user's chosen [DispatchType].
 * Returns `true` on success, `false` when no activity handled it.
 */
fun Context.dispatchUrl(uri: Uri, type: DispatchType): Boolean {
    val base = Intent(Intent.ACTION_VIEW, uri)
    val intent = when (type) {
        DispatchType.DEFAULT -> base
        DispatchType.CHOOSER -> Intent.createChooser(base, null)
    }
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

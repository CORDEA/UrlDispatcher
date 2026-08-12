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
        // TODO(plan §12): "Browser" semantics need product clarification —
        // adding CATEGORY_BROWSABLE preserves today's behavior without
        // committing to "force default browser package" until that's
        // decided.
        DispatchType.BROWSER -> base.addCategory(Intent.CATEGORY_BROWSABLE)
        DispatchType.CHOOSER -> Intent.createChooser(base, null)
    }
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

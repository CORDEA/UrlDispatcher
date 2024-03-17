package jp.cordea.urldispatcher

import android.content.Context
import android.util.Base64
import io.reactivex.Single

class LicenseLocalDataSource(
    private val context: Context
) : LicenseRepository {
    override fun getLicenses(): Single<String> =
        Single.fromCallable {
            Base64.encodeToString(
                context.assets.open("licenses.html").readBytes(),
                Base64.DEFAULT,
            )
        }
}

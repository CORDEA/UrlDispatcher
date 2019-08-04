package jp.cordea.urldispatcher

import android.content.Context
import io.reactivex.Single

class LicenseLocalDataSource(
        private val context: Context
) : LicenseRepository {
    override fun getLicenses(): Single<String> =
            Single.fromCallable {
                context.assets.open("licenses.html").bufferedReader().readText()
            }
}

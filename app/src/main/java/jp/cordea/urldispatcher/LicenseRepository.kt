package jp.cordea.urldispatcher

import io.reactivex.Single

interface LicenseRepository {
    fun getLicenses(): Single<String>
}

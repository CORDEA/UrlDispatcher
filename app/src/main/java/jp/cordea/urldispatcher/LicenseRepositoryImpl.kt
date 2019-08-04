package jp.cordea.urldispatcher

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LicenseRepositoryImpl(
        private val localDataSource: LicenseLocalDataSource
) : LicenseRepository {
    override fun getLicenses(): Single<String> =
            localDataSource.getLicenses().subscribeOn(Schedulers.io())
}

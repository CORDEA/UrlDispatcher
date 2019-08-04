package jp.cordea.urldispatcher.licenses

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LicenseNavigator(
        private val fragment: Fragment
) {
    fun finish() {
        fragment.findNavController().popBackStack()
    }
}

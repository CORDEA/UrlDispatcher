package jp.cordea.urldispatcher.main

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import jp.cordea.urldispatcher.R

class HomeNavigator(
        private val fragment: Fragment
) {
    fun navigateToAdd() {
        NavHostFragment.findNavController(fragment).navigate(R.id.addFragment)
    }

    fun showBottomSheet() {
        HomeBottomSheetDialogFragment()
                .show(fragment.childFragmentManager, HomeBottomSheetDialogFragment.TAG)
    }

    fun navigateToWeb(uri: Uri) {
        fragment.startActivity(
                Intent(Intent.ACTION_VIEW, uri)
        )
    }
}

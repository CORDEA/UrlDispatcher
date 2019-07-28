package jp.cordea.urldispatcher.main

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

class HomeNavigator(
        private val fragment: Fragment
) {
    fun navigateToAdd(url: String?) {
        NavHostFragment.findNavController(fragment)
                .navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment(url))
    }

    fun showBottomSheet(url: String) {
        HomeBottomSheetDialogFragment.newInstance(url)
                .show(fragment.childFragmentManager)
    }

    fun navigateToWeb(uri: Uri) {
        fragment.startActivity(
                Intent(Intent.ACTION_VIEW, uri)
        )
    }
}

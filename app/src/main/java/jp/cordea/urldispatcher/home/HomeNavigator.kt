package jp.cordea.urldispatcher.home

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.cordea.urldispatcher.R

class HomeNavigator(
        private val fragment: Fragment
) {
    fun navigateToEdit() {
        fragment.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToEditFragment(0L))
    }

    fun navigateToLicense() {
        fragment.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToLicenseFragment())
    }

    fun showBottomSheet(id: Long) {
        HomeBottomSheetDialogFragment.newInstance(id)
                .show(fragment.childFragmentManager)
    }

    fun navigateToWeb(uri: Uri) {
        fragment.startActivity(
                Intent(Intent.ACTION_VIEW, uri)
        )
    }

    fun showNotFoundErrorToast() {
        Toast.makeText(
                fragment.requireContext(),
                R.string.not_found_error_title,
                Toast.LENGTH_SHORT
        ).show()
    }
}

package jp.cordea.urldispatcher.main

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.add.AddFragmentArgs

class HomeBottomSheetNavigator(
        private val fragment: Fragment
) {
    fun navigateToEdit(url: String) {
        fragment.findNavController().navigate(
                R.id.addFragment,
                AddFragmentArgs(url).toBundle()
        )
    }

    fun showErrorToast(type: HomeBottomSheetViewModel.ErrorType) {
        when (type) {
            HomeBottomSheetViewModel.ErrorType.UNKNOWN ->
                Toast.makeText(
                        fragment.requireContext(),
                        R.string.failed_to_delete_url_error_title,
                        Toast.LENGTH_SHORT
                ).show()
        }
    }
}
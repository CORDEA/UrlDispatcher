package jp.cordea.urldispatcher.add

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.cordea.urldispatcher.R

class AddNavigator(
        private val fragment: Fragment
) {
    fun showErrorToast(type: AddViewModel.ErrorType) {
        Toast.makeText(
                fragment.requireContext(),
                when (type) {
                    AddViewModel.ErrorType.EMPTY_URL ->
                        R.string.url_empty_error_title
                    AddViewModel.ErrorType.UNKNOWN ->
                        R.string.failed_to_save_url_error_title
                },
                Toast.LENGTH_SHORT
        ).show()
    }

    fun finish() {
        fragment.findNavController().popBackStack()
    }
}

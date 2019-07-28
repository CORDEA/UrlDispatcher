package jp.cordea.urldispatcher.edit

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.cordea.urldispatcher.R

class EditNavigator(
        private val fragment: Fragment
) {
    fun showErrorToast(type: EditViewModel.ErrorType) {
        Toast.makeText(
                fragment.requireContext(),
                when (type) {
                    EditViewModel.ErrorType.EMPTY_URL ->
                        R.string.url_empty_error_title
                    EditViewModel.ErrorType.UNKNOWN ->
                        R.string.failed_to_save_url_error_title
                },
                Toast.LENGTH_SHORT
        ).show()
    }

    fun finish() {
        fragment.findNavController().popBackStack()
    }
}

package jp.cordea.urldispatcher.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.add.AddFragmentArgs
import jp.cordea.urldispatcher.databinding.HomeBottomSheetDialogFragmentBinding

class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val args: HomeBottomSheetDialogFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = HomeBottomSheetDialogFragmentBinding.inflate(
                inflater, container, false
        )

        binding.edit.setOnClickListener {
            findNavController().navigate(
                    R.id.addFragment,
                    AddFragmentArgs(args.url).toBundle()
            )
        }
        binding.delete.setOnClickListener {
        }

        return binding.root
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
    }

    companion object {
        private const val TAG = "HomeBottomSheetDialogFragment"
        private const val ARG_KEY = "arg"

        fun newInstance(url: String) = HomeBottomSheetDialogFragment().apply {
            bundleOf(ARG_KEY to HomeBottomSheetDialogFragmentArgs(url).toBundle())
        }
    }
}

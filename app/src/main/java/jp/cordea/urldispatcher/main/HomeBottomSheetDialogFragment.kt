package jp.cordea.urldispatcher.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.cordea.urldispatcher.databinding.HomeBottomSheetDialogFragmentBinding

class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = HomeBottomSheetDialogFragmentBinding.inflate(
                inflater, container, false
        )

        binding.edit.setOnClickListener {

        }
        binding.delete.setOnClickListener {

        }

        return binding.root
    }

    companion object {
        const val TAG = "HomeBottomSheetDialogFragment"
    }
}

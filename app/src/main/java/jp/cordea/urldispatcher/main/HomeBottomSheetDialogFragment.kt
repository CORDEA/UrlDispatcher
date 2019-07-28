package jp.cordea.urldispatcher.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.cordea.urldispatcher.MainViewModel
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.add.AddFragmentArgs
import jp.cordea.urldispatcher.databinding.HomeBottomSheetDialogFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: HomeBottomSheetViewModel by viewModel()

    private val args by lazy {
        HomeBottomSheetDialogFragmentArgs.fromBundle(requireArguments().getBundle(ARGS_KEY)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.url)
    }

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
        binding.delete.setOnClickListener { viewModel.delete() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dismiss.observe(this, Observer {
            dismiss()
            mainViewModel.requestUpdate()
        })
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
    }

    companion object {
        private const val TAG = "HomeBottomSheetDialogFragment"
        private const val ARGS_KEY = "args"

        fun newInstance(url: String) = HomeBottomSheetDialogFragment().apply {
            arguments = bundleOf(ARGS_KEY to HomeBottomSheetDialogFragmentArgs(url).toBundle())
        }
    }
}

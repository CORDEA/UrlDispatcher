package jp.cordea.urldispatcher.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.databinding.FragmentAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment() {
    private val viewModel: AddViewModel by viewModel()
    private val args: AddFragmentArgs by navArgs()

    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.init(args.url)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            viewModel.trySaveUrl(
                    binding.url.editText?.text?.toString(),
                    binding.description.editText?.text?.toString()
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.url
                .observe(this, Observer {
                    binding.url.editText?.setText(it.url)
                    binding.description.editText?.setText(it.description)
                })
        viewModel.error
                .observe(this, Observer {
                    Toast.makeText(
                            requireContext(),
                            when (it!!) {
                                AddViewModel.ErrorType.EMPTY_URL ->
                                    R.string.url_empty_error_title
                                AddViewModel.ErrorType.UNKNOWN ->
                                    R.string.failed_to_save_url_error_title
                            },
                            Toast.LENGTH_SHORT
                    ).show()
                })
        viewModel.popBackStack
                .observe(this, Observer {
                    findNavController().popBackStack()
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}

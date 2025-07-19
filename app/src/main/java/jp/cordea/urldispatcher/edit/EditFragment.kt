package jp.cordea.urldispatcher.edit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import jp.cordea.urldispatcher.databinding.EditFragmentBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditFragment : Fragment() {
    private val viewModel: EditViewModel by viewModel()
    private val navigator: EditNavigator by currentScope.inject { parametersOf(this) }
    private val args: EditFragmentArgs by navArgs()

    private lateinit var binding: EditFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fab.setOnClickListener { viewModel.trySaveUrl() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) { it?.let { navigator.showErrorToast(it) } }
        viewModel.popBackStack.observe(viewLifecycleOwner) { navigator.finish() }
    }
}

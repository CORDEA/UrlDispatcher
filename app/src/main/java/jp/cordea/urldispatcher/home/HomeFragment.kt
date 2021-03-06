package jp.cordea.urldispatcher.home


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jp.cordea.urldispatcher.MainViewModel
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.databinding.HomeFragmentBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter by currentScope.inject { parametersOf(this) }
    private val navigator: HomeNavigator by currentScope.inject { parametersOf(this) }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener { navigator.navigateToEdit() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adapterItems.observe(this, Observer { adapter.update(it) })
        mainViewModel.refresh.observe(this, Observer { viewModel.refresh() })
        viewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.licenses -> {
                    navigator.navigateToLicense()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}

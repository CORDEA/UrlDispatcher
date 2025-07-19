package jp.cordea.urldispatcher.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
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
        viewModel.adapterItems.observe(viewLifecycleOwner) { adapter.update(it) }
        mainViewModel.refresh.observe(viewLifecycleOwner) { viewModel.refresh() }
        viewModel.refresh()
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.licenses -> {
                        navigator.navigateToLicense()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }
}

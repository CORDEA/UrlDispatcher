package jp.cordea.urldispatcher.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.databinding.HomeFragmentBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter by currentScope.inject { parametersOf(this) }
    private val navigator: HomeNavigator by currentScope.inject { parametersOf(this) }

    private lateinit var binding: HomeFragmentBinding

    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener { navigator.navigateToAdd(null) }

        viewModel.adapterItems
                .subscribeBy { adapter.update(it) }
                .addTo(compositeDisposable)

        viewModel.refresh()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

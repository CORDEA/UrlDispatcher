package jp.cordea.urldispatcher.main


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.Disposable
import jp.cordea.urldispatcher.add.AddFragment
import jp.cordea.urldispatcher.databinding.FragmentMainBinding
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
//    private val adapter: MainAdapter by inject { parametersOf(this@MainActivity) }
//    private val navigator: MainNavigator by inject { parametersOf(this@MainActivity) }

    private lateinit var binding: FragmentMainBinding

    private var disposable: Disposable? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        bindScope(getOrCreateScope(SCOPE))
//        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            //            navigator.navigateToAdd()
        }

//        disposable = viewModel.adapterItems
//                .subscribeBy { adapter.update(it) }

        viewModel.refresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode != AddFragment.REQUEST_CODE) {
            return
        }
        viewModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        private const val SCOPE = "MainFragment"
    }
}

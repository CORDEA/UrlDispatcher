package jp.cordea.urldispatcher.licenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.cordea.urldispatcher.databinding.LicenseFragmentBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LicenseFragment : Fragment() {
    private val viewModel: LicenseViewModel by viewModel()
    private val navigator: LicenseNavigator by currentScope.inject { parametersOf(this) }

    private lateinit var binding: LicenseFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LicenseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.html.observe(viewLifecycleOwner) {
            binding.webView.loadData(it, "text/html", "base64")
        }
        viewModel.init()
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                navigator.finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
}

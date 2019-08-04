package jp.cordea.urldispatcher.licenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jp.cordea.urldispatcher.databinding.LicenseFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LicenseFragment : Fragment() {
    private val viewModel: LicenseViewModel by viewModel()

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

        viewModel.html.observe(this, Observer {
            binding.webView.loadData(it, "text/html", "utf-8")
        })
        viewModel.init()
    }
}

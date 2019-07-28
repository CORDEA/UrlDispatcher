package jp.cordea.urldispatcher.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.databinding.FragmentAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModel()

    private lateinit var binding: FragmentAddBinding

    private var disposable: Disposable? = null

    private val args: AddFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.url)

        disposable = viewModel.url
                .subscribeBy {
                    binding.url.editText?.setText(it.url)
                    binding.description.editText?.setText(it.description)
                }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            storeUrl()
            NavHostFragment.findNavController(this).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun storeUrl() {
        val url = binding.url.editText?.text?.toString()
        val description = binding.description.editText?.text?.toString()
        if (url.isNullOrBlank() || description.isNullOrBlank()) {
            return
        }
        viewModel.storeUrl(url, description)
    }
}

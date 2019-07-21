package jp.cordea.urldispatcher.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.cordea.urldispatcher.databinding.FragmentAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment() {

    private val viewModel: AddViewModel by viewModel()

    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            storeUrl()
//            setResult(Activity.RESULT_OK)
//            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun storeUrl() {
        val url = binding.url.editText?.text?.toString()
        val description = binding.description.editText?.text?.toString()
        if (url.isNullOrBlank() || description.isNullOrBlank()) {
            return
        }
        viewModel.storeUrl(url, description)
    }

    companion object {
        const val REQUEST_CODE = 1
    }
}

package jp.cordea.urldispatcher.add

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.databinding.ActivityAddBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddActivity : AppCompatActivity() {

    private val viewModel: AddViewModel by viewModel()

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_add)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            storeUrl()
            setResult(Activity.RESULT_OK)
            finish()
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

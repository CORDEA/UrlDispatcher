package jp.cordea.urldispatcher.add

import android.os.Bundle
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val text = binding.url.editText?.text?.toString()
        if (text.isNullOrBlank()) {
            return
        }
        viewModel.storeUrl(text)
    }
}

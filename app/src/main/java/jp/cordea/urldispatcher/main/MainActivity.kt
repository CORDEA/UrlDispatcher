package jp.cordea.urldispatcher.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.add.AddActivity
import jp.cordea.urldispatcher.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val adapter: MainAdapter by inject()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindScope(getOrCreateScope(SCOPE))
        setSupportActionBar(toolbar)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        viewModel.start()
    }

    companion object {
        const val SCOPE = "MainActivity"
    }
}

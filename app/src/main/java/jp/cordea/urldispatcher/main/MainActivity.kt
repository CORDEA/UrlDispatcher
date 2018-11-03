package jp.cordea.urldispatcher.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.add.AddActivity
import jp.cordea.urldispatcher.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val adapter: MainAdapter by inject { parametersOf(this@MainActivity) }
    private val navigator: MainNavigator by inject { parametersOf(this@MainActivity) }

    private lateinit var binding: ActivityMainBinding

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindScope(getOrCreateScope(SCOPE))
        setSupportActionBar(toolbar)
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            navigator.navigateToAdd()
        }

        disposable = viewModel.adapterItems
                .subscribeBy { adapter.update(it) }

        viewModel.refresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode != AddActivity.REQUEST_CODE) {
            return
        }
        viewModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    companion object {
        const val SCOPE = "MainActivity"
    }
}

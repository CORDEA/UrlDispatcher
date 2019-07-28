package jp.cordea.urldispatcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import jp.cordea.urldispatcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(this, R.id.main_nav_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            when (id) {
                R.id.homeFragment -> {
                    supportActionBar?.apply {
                        title = getString(R.string.title_main)
                        setDisplayHomeAsUpEnabled(false)
                    }
                }
                R.id.editFragment -> {
                    supportActionBar?.apply {
                        title = getString(R.string.title_add)
                        setDisplayHomeAsUpEnabled(true)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}

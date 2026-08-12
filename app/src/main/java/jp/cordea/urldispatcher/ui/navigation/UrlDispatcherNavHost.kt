package jp.cordea.urldispatcher.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jp.cordea.urldispatcher.R
import jp.cordea.urldispatcher.dispatchUrl
import jp.cordea.urldispatcher.ui.edit.EditScreen
import jp.cordea.urldispatcher.ui.edit.EditViewModel
import jp.cordea.urldispatcher.ui.home.HomeScreen
import jp.cordea.urldispatcher.ui.home.HomeViewModel
import jp.cordea.urldispatcher.ui.settings.SettingsScreen
import jp.cordea.urldispatcher.ui.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UrlDispatcherNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val activityNotFoundMessage = stringResource(R.string.not_found_error_title)

    NavHost(
            navController = navController,
            startDestination = Destinations.HOME,
            modifier = modifier
    ) {
        composable(Destinations.HOME) {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                    viewModel = viewModel,
                    onNewLinkClick = { navController.navigate(Destinations.edit()) },
                    onEditLink = { id -> navController.navigate(Destinations.edit(id)) },
                    onOpenSettings = { navController.navigate(Destinations.SETTINGS) },
                    onLinkOpen = { item ->
                        val ok = context.dispatchUrl(item.uri, item.dispatchType)
                        if (!ok) {
                            Toast.makeText(context, activityNotFoundMessage, Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
            )
        }

        composable(
                route = Destinations.EDIT_ROUTE,
                arguments = listOf(
                        navArgument(Destinations.EDIT_ARG_ID) {
                            type = NavType.LongType
                            defaultValue = 0L
                        }
                )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(Destinations.EDIT_ARG_ID) ?: 0L
            val viewModel = koinViewModel<EditViewModel> { parametersOf(id) }
            EditScreen(
                    viewModel = viewModel,
                    onFinished = { navController.popBackStack() }
            )
        }

        composable(Destinations.SETTINGS) {
            val viewModel = koinViewModel<SettingsViewModel>()
            SettingsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
            )
        }
    }
}

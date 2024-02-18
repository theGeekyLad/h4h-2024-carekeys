package com.thegeekylad.carekeys.parent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thegeekylad.carekeys.parent.api.JavalinInstance
import com.thegeekylad.carekeys.parent.nav.Screen
import com.thegeekylad.carekeys.parent.ui.screen.HomeScreen
import com.thegeekylad.carekeys.parent.ui.screen.ProfileScreen
import com.thegeekylad.carekeys.parent.ui.theme.CareKeysParentTheme
import com.thegeekylad.carekeys.parent.viewmodel.AppViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: AppViewModel by viewModels()

        setContent {
            val navController = rememberNavController()

            CareKeysParentTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            title = {
                                Text("CareKeys (Parent)")
                            },
                            actions = {
                                IconButton(
                                    onClick = { navController.navigate(Screen.Settings.route) }
                                ) {
                                    Icon(
                                        Icons.Rounded.AccountCircle,
                                        contentDescription = null,
//                                        tint = MaterialTheme.colorScheme.surface,
                                    )
                                }
                            }
                        )
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(it),
                        enterTransition = {
                            slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                                -fullWidth / 3
                            } + fadeIn(
                                animationSpec = tween(durationMillis = 200)
                            )
                        },
                        exitTransition = {
                            ExitTransition.None
                        }
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(viewModel = viewModel)
                        }

                        composable(Screen.Settings.route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }

        lifecycle.addObserver(JavalinInstance(viewModel, applicationContext))
    }
}
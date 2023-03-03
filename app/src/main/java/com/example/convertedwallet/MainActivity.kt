package com.example.convertedwallet

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.convertedwallet.ui.main_screen.MainScreen
import com.example.convertedwallet.ui.theme.ConvertedWalletTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConvertedWalletTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen()
//                    val navController = rememberNavController()
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.MainScreen.route
//                    ) {
//                        composable(
//                            route = Screen.MainScreen.route
//                        ) {
//                            MainScreen(navController = navController)
//                        }
//                        composable(
//                            route = Screen.AddEditMoneyScreen.route +
//                                    "?moneyId={moneyId}",
//                            arguments = listOf(
//                                navArgument(
//                                    name = "moneyId"
//                                ) {
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                },
//                            )
//                        ) {
//                            AddEditMoneyScreen(
//                                navController = navController
//                            )
//                        }
//                    }
                }
            }
        }
    }
}


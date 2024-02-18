package com.thegeekylad.carekeys.parent.nav


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
}

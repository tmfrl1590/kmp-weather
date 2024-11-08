package com.kmp.weather

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kmp.weather.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            MainNavigation()
        }
    }
}
package com.therxmv.napoleon

import androidx.compose.runtime.Composable
import com.therxmv.leonui.theme.LeonTheme
import com.therxmv.napoleon.navigation.RootNavigation
import com.therxmv.napoleon.navigation.component.RootComponent
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    val rootComponent = koinInject<RootComponent>()

    LeonTheme {
        RootNavigation(rootComponent)
    }
}
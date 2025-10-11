package com.therxmv.napoleon

import androidx.compose.runtime.Composable
import com.therxmv.napoleon.navigation.RootNavigation
import com.therxmv.napoleon.navigation.component.RootComponent
import com.therxmv.napoleon.ui.theme.NapoleonTheme
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    val rootComponent = koinInject<RootComponent>()

    NapoleonTheme {
        RootNavigation(rootComponent)
    }
}
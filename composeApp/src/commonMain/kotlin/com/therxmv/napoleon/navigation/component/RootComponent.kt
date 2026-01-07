package com.therxmv.napoleon.navigation.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.therxmv.napoleon.navigation.destination.child.Child
import com.therxmv.napoleon.navigation.destination.slot.Slot

@Stable
interface RootComponent : BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>
    val slot: Value<ChildSlot<*, Slot>>

    fun onBackClicked()

    fun canGoBack(): Boolean
}
package com.therxmv.leonres

import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

// TODO p1 check performance of runBlocking
fun getSyncString(resource: StringResource): String =
    runBlocking { getString(resource) }

fun getSyncString(resource: StringResource, vararg formatArgs: Any): String =
    runBlocking { getString(resource, *formatArgs) }
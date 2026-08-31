package com.therxmv.leonres

import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

/**
 * Synchronously resolves a string resource for non-composable contexts (e.g. repositories, Decompose components).
 *
 * Uses [runBlocking] — safe here because Compose Resources caches a resource file's contents in memory after
 * its first read, and every current call site is reached only after other screens (Dashboard/BottomNav) have
 * already resolved strings from the same underlying resource file via composable `stringResource`/`getString`
 * calls. Do not call this before any UI has composed, or from a hot path — it will perform real I/O on a cache miss.
 */
fun getSyncString(resource: StringResource): String =
    runBlocking { getString(resource) }

fun getSyncString(resource: StringResource, vararg formatArgs: Any): String =
    runBlocking { getString(resource, *formatArgs) }
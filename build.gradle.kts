plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)

    alias(libs.plugins.firebase).apply(false)
    alias(libs.plugins.firebase.crashlytics).apply(false)

    alias(libs.plugins.kotlin.multiplatform.library) apply false
}

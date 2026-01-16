plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidLibrary {
        namespace = libs.versions.project.applicationId.get().plus(".leonui")
        compileSdk = libs.versions.project.compileSdk.get().toInt()
        minSdk = libs.versions.project.minSdk.get().toInt()
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "leonuiKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.tooling.preview)
                implementation(libs.compose.material3)
                implementation(libs.compose.material.icons)

                implementation(libs.icons.feather)
            }
        }

        androidMain {
            dependencies {
                // TODO For some reason @Preview doesn't work without these dependencies
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.tooling)
                implementation("androidx.emoji2:emoji2:1.5.0")
                implementation("androidx.customview:customview-poolingcontainer:1.1.0")
            }
        }

        iosMain {
            dependencies {

            }
        }
    }

}
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
    val xcfName = "leonuiKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.icons.feather)
            }
        }

        androidMain {
            dependencies {
                // For some reason @Preview doesn't work without these dependencies
                implementation(libs.androidx.activity.compose)
                implementation(compose.uiTooling)
                implementation(compose.preview)
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
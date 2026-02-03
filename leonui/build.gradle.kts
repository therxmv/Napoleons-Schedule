plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    androidLibrary {
        namespace = "com.therxmv.leonui"
        compileSdk = libs.versions.project.compileSdk.get().toInt()
        minSdk = libs.versions.project.minSdk.get().toInt()
    }

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
                implementation(project(":datetime"))

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
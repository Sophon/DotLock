plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

group = "io.github.sophon.firefrog"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":feat:deadlock"))

            implementation(libs.bundles.ktor)

            implementation(libs.koin.core)
            implementation(libs.napier)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlin.date.time)
        }

        jvmMain.dependencies {
            implementation(libs.kord)
            implementation(libs.ktor.cio)
            implementation(libs.ktor.slf)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.test.assertk)
            implementation(libs.test.turbine)
        }
    }
}
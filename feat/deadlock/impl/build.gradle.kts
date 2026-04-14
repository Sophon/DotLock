import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildkonfig)
}

group = "io.github.sophon.firefrog"
version = "1.0-SNAPSHOT"

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))

            implementation(libs.bundles.ktor)
            implementation(libs.napier)
            implementation(libs.kotlin.date.time)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.testJunit)
            implementation(libs.test.assertk)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.test.turbine)
            implementation(libs.junit)
        }
    }
}

val featureVersion = "0.1.0"
buildkonfig {
    packageName = "io.github.sophon.deadlock.impl"

    defaultConfigs {
        buildConfigField(STRING, "VERSION", featureVersion)
    }
}
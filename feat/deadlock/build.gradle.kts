plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

group = "io.github.sophon.firefrog"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":core"))

    implementation(libs.bundles.ktor)
    implementation(libs.napier)
    implementation(libs.kotlin.date.time)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.testJunit)
    testImplementation(libs.test.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.turbine)
    testImplementation(libs.junit)
}

tasks.test {
    useJUnitPlatform()
}
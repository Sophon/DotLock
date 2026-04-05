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
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.date.time)

    implementation(libs.bundles.ktor)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.slf)

    implementation(libs.napier)

    api(libs.koin.core)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.test.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.turbine)
}

tasks.test {
    useJUnitPlatform()
}

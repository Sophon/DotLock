plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

group = "io.github.sophon.dotlock"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.date.time)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.test.assertk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.test.turbine)
}

tasks.test {
    useJUnitPlatform()
}

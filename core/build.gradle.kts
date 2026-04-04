plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        binaries {
            executable {
                mainClass = "io.github.sophon.dotlock.core.MainKt"
            }
        }
    }

    sourceSets {
        commonMain.dependencies { }
        jvmMain.dependencies { }
    }
}
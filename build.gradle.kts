plugins {
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.LagradOst:CloudStream:master-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    archiveFileName.set("extension-release.cs3")
    from(sourceSets.main.get().output)
}

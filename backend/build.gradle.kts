plugins {
    kotlin("jvm")
    application
    kotlin("plugin.serialization")
}

group = "com.github.warriorzz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://schlaubi.jfrog.io/artifactory/envconf/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("dev.schlaubi", "envconf", "1.1")
    implementation(platform("io.ktor:ktor-bom:1.6.0"))
    implementation("io.ktor", "ktor-server-core")
    implementation("io.ktor", "ktor-server-cio")
    implementation("io.ktor", "ktor-serialization")
    implementation("dev.kord", "kord-core", "0.7.1")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.5.0")
    implementation("org.litote.kmongo", "kmongo-coroutine-serialization", "4.2.7")

    implementation("de.nycode", "bcrypt","2.1.0")
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.6")
    implementation("org.slf4j", "slf4j-simple", "1.7.29")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

application {
    mainClass.set("com.github.warriorzz.backend.LauncherKt")
}

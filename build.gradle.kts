plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.0"
    application
}

group = "com.github.warriorzz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.cdimascio", "dotenv-kotlin", "6.2.2")
    implementation(platform("io.ktor:ktor-bom:1.6.0"))
    implementation("io.ktor", "ktor-client-okhttp")
    implementation("io.ktor", "ktor-client-serialization")

    implementation("dev.inmo", "krontab", "0.6.0")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.5.0")
    implementation("org.litote.kmongo", "kmongo-coroutine-serialization", "4.2.7")
    implementation("io.github.microutils", "kotlin-logging-jvm", "2.0.6")
    implementation("org.slf4j", "slf4j-api", "1.7.30")

    val junitVersion = "5.7.2"
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

application {
    mainClass.set("com.github.warriorzz.dynamicbio.LauncherKt")
}

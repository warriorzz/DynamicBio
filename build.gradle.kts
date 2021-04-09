plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    application
}

group = "com.github.warriorzz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("io.github.cdimascio", "dotenv-kotlin", "6.2.2")
    implementation("io.ktor", "ktor-client-okhttp", "1.5.2")
    implementation("io.ktor", "ktor-client-serialization", "1.5.2")

    implementation("dev.inmo", "krontab", "0.5.0")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.4.3")
    api("org.litote.kmongo", "kmongo-coroutine-serialization", "4.2.3")

    val junitVersion = "5.6.0"
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

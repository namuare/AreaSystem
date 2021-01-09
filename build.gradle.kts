plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
}

group = "me.mocha.spongeplugin"
version = "1.0-SNAPSHOT"
description = "area system for sponge api"

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven")
    maven("http://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.sk89q.worldedit:worldedit-sponge:6.1.7-SNAPSHOT")
    implementation("com.sk89q.worldedit:worldedit-core:6.1.4-SNAPSHOT")

    val sponge = create("org.spongepowered:spongeapi:7.3.0")
    api(sponge)
    kapt(sponge)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

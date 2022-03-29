rootProject.name = "otuskotlin-coins"

pluginManagement {
    val kotlinVersion: String by settings
    val shadowJarPluginVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        id("com.github.johnrengelman.shadow") version shadowJarPluginVersion
    }
}

include("hello-world")
include("dsl-example")
include("web-ui")

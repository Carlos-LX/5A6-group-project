// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id ("org.jlleitschuh.gradle.ktlint") version "12.0.2"
}
allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
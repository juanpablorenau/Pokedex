// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0-rc03" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.2.0-rc03" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

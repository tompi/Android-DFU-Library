plugins {
    // https://github.com/NordicSemiconductor/Android-Gradle-Plugins/blob/main/plugins/src/main/kotlin/AndroidFeatureConventionPlugin.kt
    alias(libs.plugins.nordic.feature)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "no.nordicsemi.android.dfu.profile.scanner"
}

dependencies {
    implementation(libs.nordic.blek.core)
    implementation(libs.nordic.blek.uiscanner)
    implementation(libs.nordic.navigation)
}

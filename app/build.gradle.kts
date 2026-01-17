plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  // Required for Kotlin 2.0+ when Compose is enabled
  id("org.jetbrains.kotlin.plugin.compose")
}

android {
  namespace = "com.atlas.android"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.atlas.android"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions { jvmTarget = "17" }

  buildFeatures { compose = true }
  // With Kotlin 2.0 + compose plugin, you should NOT pin kotlinCompilerExtensionVersion here.
  // Compose compiler version is managed by the plugin.
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.compose)

  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui.tooling.preview)
  debugImplementation(libs.compose.ui.tooling)
}

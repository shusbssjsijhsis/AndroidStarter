plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id("org.jetbrains.kotlin.plugin.compose")
}

import java.util.Properties

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

  // ---- Signing (Release) ----
  val props = Properties()
  val propsFile = rootProject.file("keystore.properties")

  signingConfigs {
    create("release") {
      if (propsFile.exists()) {
        propsFile.inputStream().use { props.load(it) }
        val ksPath = props.getProperty("storeFile") ?: ""
        storeFile = rootProject.file(ksPath)   // IMPORTANT: resolve relative to repo root
        storePassword = props.getProperty("storePassword")
        keyAlias = props.getProperty("keyAlias")
        keyPassword = props.getProperty("keyPassword")
      }
    }
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
    }
    getByName("release") {
      isMinifyEnabled = false
      signingConfig = signingConfigs.getByName("release")
    }
  }
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

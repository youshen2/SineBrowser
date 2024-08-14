plugins {
    id("com.android.application")
}

android {
    namespace = "moye.brower.stringbrower"
    compileSdk = 33

    defaultConfig {
        applicationId = "moye.brower.stringbrower"
        minSdk = 19
        targetSdk = 27
        versionCode = 1
        versionName = "1.0"
        resConfigs("zh")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
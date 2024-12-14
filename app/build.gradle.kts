plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}
dependencies {
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation ("com.google.android.material:material:1.5.0")



    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    // Apache POI pour lire les fichiers Excel
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation ("org.apache.poi:poi:5.2.3")
    // iText pour lire les fichiers PDF
    implementation("com.itextpdf:itext7-core:7.2.3")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    // Firebase (authentification, analytics)
    implementation("com.google.firebase:firebase-auth")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.1")
    implementation ("com.google.firebase:firebase-messaging:23.0.0")

    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
}


android {
    namespace = "com.example.projet_absences_enseignants"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projet_absences_enseignants"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    implementation(libs.coordinatorlayout)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.android)
    implementation(libs.firebase.database)
    dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation(libs.coordinatorlayout)
        implementation(libs.recyclerview)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }

}
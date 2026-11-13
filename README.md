# 🔐 URL & QR Phishing Detector (Android)

An Android application designed to help users identify unsafe or phishing URLs and QR codes using on-device machine learning, with secure local and cloud-based data storage.

---

## 📌 Problem Statement
Many individuals continue to struggle with identifying unsafe or phishing URLs, especially those received via SMS, WhatsApp, and email. Limited digital awareness increases the risk of data theft, identity fraud, and online scams.

In alignment with **UN Sustainable Development Goal 4 (Quality Education)**, this project aims to enhance digital literacy and cybersecurity awareness by providing users with an educational and practical mobile application for detecting malicious links.

---

## 🎯 Project Objectives
- Detect phishing URLs and QR codes in real time.
- Improve users’ cybersecurity awareness and decision-making.
- Preserve user privacy through on-device machine learning.
- Provide secure storage and review of scan history.

---

## 🧠 Key Features
- 🔍 **URL Scanning** using a locally deployed machine learning model (TensorFlow Lite).
- 📷 **QR Code Scanning** using CameraX and ML Kit.
- 🧠 **On-device AI Prediction** (no cloud-based URL analysis).
- 🗃 **Local Storage** with Room Database for fast access.
- ☁️ **Cloud Synchronization** with Firebase Firestore for authenticated users.
- 🔐 **User Authentication** via Firebase Authentication.
- 📊 **Scan History Management** (view, delete, clear).

---

## 🏗 Architecture Overview
The application follows a clean MVVM-based architecture:

- **UI Layer:** Jetpack Compose
- **ViewModel:** Handles business logic and state
- **Local Data Layer:** Room (SQLite)
- **Remote Data Layer:** Firebase Firestore
- **Authentication:** Firebase Auth
- **ML Engine:** TensorFlow Lite (On-device)

---

## 💾 Data Management
### Local (Room)
- Stores scanned URLs, results, and timestamps.
- Enables fast access and offline viewing.

### Cloud (Firestore)
- Stores scan history per authenticated user.
- Data is organized under `users/{uid}/history`.
- Enables secure backup and synchronization across sessions.

---

## 🔒 Privacy & Security
- URL analysis is performed **locally on the device**.
- No URLs are sent to external servers for prediction.
- User credentials are managed securely by Firebase Authentication.
- Cloud storage is accessible only to authenticated users.

---

## 🛠 Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Room Database**
- **Firebase Authentication**
- **Firebase Firestore**
- **TensorFlow Lite**
- **CameraX**
- **ML Kit (Barcode Scanning)**

---

## ⚙️ Getting Started

### Requirements
- **Android Studio** (recent version: Ladybug or newer)
- **JDK 17+**: the JDK bundled with Android Studio works
- **Android SDK 36** (Android Studio prompts you to install it)
- An Android phone or emulator running **Android 8.0 (API 26)** or higher
- A camera is needed for QR scanning (on an emulator, use the virtual camera)

### 1. Clone the project
```bash
git clone https://github.com/esraaH2/Phishing-URL-Detection-Mobile-app.git
```

### 2. Open it in Android Studio
1. **File → Open** and select the cloned folder.
2. Wait for **Gradle sync** to finish. The Gradle wrapper (8.13) and all libraries download automatically.
3. If Android Studio asks to install SDK 36 or build tools, accept.

### 3. Firebase setup
Login, sign-up and cloud history use **Firebase Authentication** and **Cloud Firestore**.
The repository already includes `app/google-services.json`, so the app builds and runs as-is against the project's Firebase backend.

To use **your own** Firebase project instead:
1. Create a project in the [Firebase Console](https://console.firebase.google.com/).
2. Add an Android app with the package name `com.example.a199870_esra_hussein_ulrdetector`.
3. Download `google-services.json` and replace `app/google-services.json`.
4. In **Authentication → Sign-in method**, enable **Email/Password**.
5. Create a **Cloud Firestore** database. Scan history is stored under `users/{uid}/history`.

### 4. Run the app
Select a device or emulator and press **Run ▶**, or build from the command line:

```bash
# macOS / Linux (first time only: chmod +x gradlew)
./gradlew assembleDebug
# Windows
gradlew.bat assembleDebug
```

The APK is created at `app/build/outputs/apk/debug/app-debug.apk`. To install it on a connected phone:

```bash
./gradlew installDebug
```

### 5. Using the app
1. **Sign up** with an email and password, or log in.
2. Enter a URL on the Home screen and tap **Scan URL**, or tap **Scan QR Code**.
3. The on-device model (`app/src/main/assets/phishing_light.tflite`) classifies the URL. No URL is sent to a server for prediction.
4. Tap **View History** to view, delete or clear past scans.

### Project structure
```
app/src/main/
├── assets/phishing_light.tflite   # On-device TensorFlow Lite model
└── java/com/example/a199870_esra_hussein_ulrdetector/
    ├── MainActivity.kt            # Navigation host
    ├── UrlViewModel.kt            # URL feature extraction + prediction logic
    ├── navigation/NavRoutes.kt    # Screen routes
    ├── ui/                        # Compose screens (Welcome, Login, SignUp, Home, QR, Result, History)
    └── data/
        ├── TfLiteModel.kt         # Loads and runs the TFLite model
        ├── auth/                  # Firebase Authentication
        ├── local/                 # Room database (local history)
        └── remote/                # Firestore history sync
```

---

## 🚀 Future Enhancements
- Cloud-to-local history synchronization.
- Phishing explanation and educational feedback.
- Multi-language support.
- Enhanced model accuracy with future training.

---

## 🎓 Academic Context
- Mobile application development
- Data persistence
- Cloud integration
- Secure system design
- Digital education and awareness

---

## 📬 Contact
**Developer:** Esra  

---

> *This project demonstrates the integration of on-device intelligence with cloud-based data management to deliver a secure, educational, and user-friendly cybersecurity tool.*

# 🔐 URL & QR Phishing Detector (Android)

An Android application designed to help users identify unsafe or phishing URLs and QR codes using on-device machine learning, with secure local and cloud-based data storage.

---

## 📌 Problem Statement
Many individuals in Malaysia continue to struggle with identifying unsafe or phishing URLs, especially those received via SMS, WhatsApp, and email. Limited digital awareness increases the risk of data theft, identity fraud, and online scams.

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

## 🚀 Future Enhancements
- Cloud-to-local history synchronization.
- Phishing explanation and educational feedback.
- Multi-language support.
- Enhanced model accuracy with future training.

---

## 🎓 Academic Context
This project was developed as part of an academic assignment focusing on:
- Mobile application development
- Data persistence
- Cloud integration
- Secure system design
- Digital education and awareness

---

## 📬 Contact
**Developer:** Esra Hussein  
📧 Email: *(optional)*  
🔗 LinkedIn: *(optional)*

---

> *This project demonstrates the integration of on-device intelligence with cloud-based data management to deliver a secure, educational, and user-friendly cybersecurity tool.*

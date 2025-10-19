# 📝 TodoZap — Cloud-Sync Notes & Tasks

A cross-device notes and task management Android app with offline and cloud synchronization. Users can create, edit, and organize tasks while seamlessly syncing across devices. The app ensures smooth offline operation and reliable conflict handling when reconnecting to the backend.

## 🚀 Features
- ✅ Create, edit, and delete notes and tasks  
- ✅ Offline-first functionality with local caching  
- ✅ Cloud synchronization using custom REST API  
- ✅ Background sync with WorkManager for seamless updates  
- ✅ Conflict resolution to prevent data loss  
- ✅ Modular architecture for maintainability and scalability  

## 🧩 Tech Stack
- **Language:** Kotlin  
- **UI:** XML / Jetpack Compose  
- **Architecture:** MVVM, Modular Design  
- **DI:** Koin  
- **Networking:** Retrofit / OkHttp  
- **Database:** Room  
- **Other:** WorkManager, Notifications  

## 📸 Screenshots
| Start | Home | Detail | Settings | 
|------------|------------|------------|------------|
| ![Start](https://github.com/user-attachments/assets/19fab9dc-76f0-49d1-b55a-f4ce5b1510ce) | ![Home](https://github.com/user-attachments/assets/b1ad7da1-0450-4e2c-8c04-9983ef623694) | ![Detail](https://github.com/user-attachments/assets/c3b8b2c5-bbad-4450-aef7-d9fa2a6128c3) | ![Settings](https://github.com/user-attachments/assets/4d0cee62-6753-4c45-a3fd-8e136ac6fcea) |

## ⚙️ How It Works
TodoZap uses a **local Room database** to store notes and tasks offline. The app synchronizes with a **custom PHP REST backend** via Retrofit and OkHttp, and **WorkManager** ensures background sync even when the app is closed. Conflict-handling logic guarantees data integrity across devices.

## 🧠 Highlights / Learning Outcomes
- Built full offline/online sync logic with background workers  
- Designed a modular architecture to enable fast feature development  
- Implemented efficient conflict resolution and caching for smooth UX

## 📂 Installation
```bash
# Clone the repo
git clone https://github.com/sean-nam-dev/todozap.git

# Open in Android Studio and run the app









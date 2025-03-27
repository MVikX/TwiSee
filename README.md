# TwiSee  
**Companion app** for Twitch built from scratch using **Kotlin + Jetpack Compose**

---

## Setup  
You need [Android Studio Hedgehog (or newer)](https://developer.android.com/studio) to be able to build the app.

---

### API Keys

You need to register your own Twitch app at the [Twitch Developer Console](https://dev.twitch.tv/console/apps)

When creating an OAuth app, set the redirect URI to:  
`twisee://open`

After receiving your keys, add them to your `local.properties` file in the root directory:
```
TWITCH_CLIENT_ID = your_client_id
TWITCH_CLIENT_SECRET = your_client_secret
TWITCH_REDIRECT_URI = twisee://open
```

---

## Release  
If you just want to try the app without building:  
➡ [Download APK from Releases](https://github.com/MVikX/TwiSee/releases/tag/v1.0.0)

---

## Features  

- 🌙 Light & Dark theme toggle  
- 🔐 Login via Twitch OAuth  
- 📺 Browse top live streams  
- 👤 Profile screen with avatar  
- ⚙️ Settings and theme preferences

---

## Author  
👤 **MViKX**  
GitHub: [MViKX](https://github.com/MVikX) 
Telegram: [@MViKX](https://t.me/MViKX)

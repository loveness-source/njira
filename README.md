# Njira — Android app (Kotlin)

A public-transport navigation prototype for Zambia's informal minibus system.
It uses the phone GPS to find nearby stops, lists minibus route options with
fares, shows a step-by-step journey, and displays live-updating minibuses.

## What you need
- **Android Studio** (Koala / Ladybug or newer). This is the tool to use —
  not Visual Studio. Android Studio bundles the JDK (17), the Android SDK and
  Gradle, and builds the APK for you.
- On first open it downloads Gradle 8.7 and the dependencies automatically
  (an internet connection is needed once).

## Build & run
1. Unzip `NjiraApp.zip`.
2. Android Studio → **File ▸ Open** → select the `NjiraApp` folder.
3. Wait for **Gradle sync** to finish. If it offers to upgrade the Android
   Gradle Plugin or Gradle, you can accept.
4. **Run** on an emulator or a connected phone (the green ▶ button), or build
   the installable file with **Build ▸ Build App Bundle(s) / APK(s) ▸ Build APK(s)**.
   The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.
5. When the app asks for **location permission**, allow it to see GPS-based
   nearby stops. If denied, it falls back to a default Town location.

## What it does (maps to the project requirements)
- **Location-based + GPS sensor** — `LocationProvider` uses the fused location
  provider; `TransportRepository.nearbyStops()` sorts stops by real distance.
- **REST (network)** — `NjiraApi` is a Retrofit interface for a live backend.
  The prototype serves built-in Lusaka data so it runs with no server; switch
  to the network by calling the API instead of the local lists.
- **UI** — four screens (Home, Journey results, Journey details, Live tracking)
  following the wireframes in the report.

## Structure (MVVM-style layering)
```
app/src/main/
  AndroidManifest.xml
  java/zm/njira/app/
    HomeActivity / ResultsActivity / DetailsActivity / LiveActivity   (UI)
    data/  Models, LocationProvider, NjiraApi, TransportRepository     (data)
    ui/    StopAdapter, RouteAdapter, MinibusAdapter                   (lists)
  res/  layout/ , values/ , drawable/
```

## Notes
- The app runs entirely on built-in data, so no API keys or backend are needed.
- Minimum Android 7.0 (API 24); target/compile SDK 34.
- If Gradle sync complains about versions, accept Android Studio's upgrade
  prompt; the code itself does not need changes.

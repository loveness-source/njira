# How to get an installable Njira APK for your phone

This project is complete and ready to build. An `.apk` file could not be
compiled inside the chat itself (that sandbox blocks Google's Android build
servers), so the APK has to be built somewhere with normal internet access.
Below are two reliable ways to do it. **Option A needs no software installed
on your computer** — GitHub builds the APK for you in the cloud and you just
download the finished file.

---

## Option A — Let GitHub build the APK for you (recommended, nothing to install)

You upload this project to a free GitHub account, and an automated job
(already set up for you in `.github/workflows/android.yml`) compiles the APK
on GitHub's servers. You then download the finished `app-debug.apk`.

1. **Create a free account** at https://github.com if you don't have one.

2. **Make a new repository:** click the **+** (top right) → **New repository**.
   Give it a name like `njira-app`, leave it Public or Private, and click
   **Create repository**.

3. **Upload the project files:** on the new repository page click
   **uploading an existing file**. Unzip `NjiraApp.zip` on your computer first,
   then drag the *contents* of the `NjiraApp` folder (including the hidden
   `.github` folder) into the upload box. Click **Commit changes**.

   > Tip: if the `.github` folder is hidden on your computer, enable
   > "show hidden files", or upload the folder from the command line with
   > `git` — the important thing is that `.github/workflows/android.yml`
   > ends up in the repository.

4. **Watch it build:** open the **Actions** tab of your repository. A job
   called **Build Njira APK** starts automatically. It takes roughly 3–5
   minutes. A green tick means it succeeded.

5. **Download the APK:** click the finished run, scroll down to **Artifacts**,
   and download **njira-debug-apk**. Inside the downloaded `.zip` is
   `app-debug.apk` — that is your installable app.

6. **Install on your phone** — see *Installing on your phone* below.

---

## Option B — Build it yourself in Android Studio

Use this if you'd rather build locally, or want to change the app.

1. **Download Android Studio** (free): https://developer.android.com/studio
   and install it. On first launch it downloads the Android SDK automatically.

2. **Open the project:** *File ▸ Open* and select the unzipped `NjiraApp`
   folder. Let Gradle finish syncing (first sync downloads dependencies and
   can take a few minutes).

3. **Build the APK:** menu *Build ▸ Build App Bundle(s) / APK(s) ▸ Build APK(s)*.

4. When it finishes, a small notice appears in the bottom-right with a
   **locate** link. The file is at:

   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

5. Copy that file to your phone and install it — see below.

You can also build from a terminal inside the project folder:

```
./gradlew assembleDebug        # macOS / Linux
gradlew.bat assembleDebug      # Windows
```

---

## Option C — Android Studio Cloud (run Android Studio in your browser)

Google runs a free, browser-based version of Android Studio called
**Android Studio Cloud**. It streams the full Linux version of Android Studio
(SDK and Android emulator already installed) from a machine in Google's cloud,
so you install nothing — and you can *watch Njira run* on an emulator right in
your browser.

**How it fits together:** Android Studio Cloud opens projects from GitHub, so
the project goes on GitHub first either way — and the moment it's there, the
build robot included in this project (`.github/workflows/android.yml`) compiles
`app-debug.apk` for you automatically. So GitHub hands you the APK for your
phone, and Android Studio Cloud gives you a live emulator to see and demo the
app. Same upload, both rewards.

### C1. Put the project on GitHub (once)

1. Create a free account at https://github.com (Sign up → verify email).
2. Click the **+** in the top-right → **New repository** → name it
   `njira-app` → **Create repository**.
3. On the new repository page, click **uploading an existing file**.
   Unzip `NjiraApp.zip` on your computer, open the unzipped `NjiraApp` folder,
   select **everything inside it** and drag it into the upload box, then click
   **Commit changes**.

   > The `.github` and `.gitignore` items are hidden files. Turn on hidden
   > files first (Windows Explorer: View ▸ Show ▸ Hidden items; Mac Finder:
   > press Cmd+Shift+.) so they get uploaded too — `.github` is the build
   > robot. If it gets left out, the app still opens in Android Studio Cloud;
   > you just lose the automatic APK.

4. Open the **Actions** tab. A job called **Build Njira APK** will already be
   running — that's your APK being made (see C4).

### C2. Open the project in Android Studio Cloud

1. In your browser go to **https://studio.firebase.google.com/new/android-studio**
   and sign in with any Google account (free; no card).
2. Accept the preview terms if asked, and let the workspace start — you'll see
   the familiar Android Studio window appear inside the browser tab after a
   minute or two.
3. Open your repository: from the welcome screen choose the option to get a
   project from version control / GitHub, paste your repository URL
   (`https://github.com/your-username/njira-app`) and confirm. A public
   repository clones with no extra sign-in; a private one will ask you to
   authorise GitHub.
4. **Let Gradle sync.** The cloud machine downloads all dependencies — allow
   several minutes the first time. When the progress bar settles with no red
   errors, the project is ready.

### C3. Run Njira on the in-browser emulator

1. In the device selector (top toolbar), pick a small pre-configured device —
   users report **Pixel 8a (API 35)** or **Small Phone (API 35)** run best in
   the preview.
2. Press the green **Run ▶** button. First boot of the emulator is slow —
   give it a few minutes; it speeds up once warmed.
3. Njira launches on the virtual phone: allow the location permission and try
   the flow (Nearby stops → destination → routes → details → live tracking).
   Two known preview quirks: the emulator has no sound, and it can feel
   laggy — that's the streaming, not your app.

### C4. Get the APK onto your phone

The streamed Android Studio has no simple "download this file to my computer"
button, so the easiest way to get the installable file is the one GitHub
already did for you in step C1.4:

1. On any device — including your phone's browser — log in to GitHub and open
   your repository → **Actions** tab.
2. Click the finished **Build Njira APK** run (green tick), scroll to
   **Artifacts**, and download **njira-debug-apk**.
3. Unzip it — inside is `app-debug.apk`. Install it (next section).

(You can still use *Build ▸ Build App Bundle(s) / APK(s) ▸ Build APK(s)*
inside Android Studio Cloud to prove the project compiles — the file just
stays on the cloud machine.)

> Notes: Android Studio Cloud is a free **preview** and Google has announced
> support ends **22 March 2027** — fine for building and testing now, but for
> long-term work a local install (Option B) is the stable home. If the site
> won't open a workspace in your region or browser, nothing is lost: the APK
> still arrives via the Actions tab (Option A), which works from anywhere.

---

## Installing on your phone

`app-debug.apk` is a normal Android app file. Because it doesn't come from the
Play Store, Android asks you to allow it once:

1. Transfer `app-debug.apk` to the phone (email it to yourself, use a USB
   cable, Google Drive, WhatsApp to yourself, etc.).
2. Tap the file. Android will say installs from this source are blocked —
   tap **Settings** and turn on **Allow from this source** (this is the normal
   prompt for any app installed outside the Play Store).
3. Go back and tap **Install**, then **Open**.
4. When the app asks for **location permission**, tap **Allow** — Njira needs
   GPS to find the minibus stops nearest to you.

> This is a *debug* build, which is exactly what you want for testing on your
> own phone. A *release* build (for publishing on the Play Store) would need to
> be signed with your own key — that's a later step and isn't required to test.

---

## What if the build fails?

The project is configured to build cleanly (Gradle wrapper included, SDK 34,
Java 17). If a GitHub build ever shows a red mark, open the failed step in the
**Actions** log — the error message names the exact problem. The most common
causes are an incomplete upload (the `.github` folder or `gradlew` file was
left out) or a temporary network hiccup on the runner, which a re-run fixes.

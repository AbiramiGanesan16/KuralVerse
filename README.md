# KuralVerse

**KuralVerse** is a full-stack Java web application for reading and exploring all 1,330 couplets of the *Thirukkural*. It pairs a responsive Tamil-first interface with a lightweight Core Java HTTP server and a local dataset, so the core reading experience works without an external Kural API.

## Features

- Browse all **1,330 Thirukkural** couplets.
- View Tamil verses, Tamil explanations, and English translations.
- Find a Kural by number and jump to a random Kural.
- Explore all **133 chapters** through the book view, organised under Arathuppaal, Porutpaal, and Inbathuppaal.
- Move between Kurals or chapters with on-screen controls and keyboard navigation.
- Save favourite Kurals in the browser using local storage.
- Copy or share a Kural.
- Switch between dark and light themes.
- Listen to Tamil or English narration through the TTS endpoint (requires internet access).

## Tech Stack

- **Backend:** Core Java using `com.sun.net.httpserver.HttpServer`
- **Frontend:** HTML, CSS, and vanilla JavaScript
- **Data:** Local JSON dataset in `data/thirukkural.json`

## Requirements

- Java **11 or later** (`java.net.http.HttpClient` is used)
- A modern web browser

## Run Locally

Clone the repository and run the application from the project root.

### Windows

```bat
build_and_run.bat
```

### macOS / Linux

```bash
chmod +x build_and_run.sh
./build_and_run.sh
```

### Manual command

```bash
javac ThirukkuralServer.java
java ThirukkuralServer
```

Then open [http://localhost:8080](http://localhost:8080) in your browser. Stop the server with `Ctrl+C`.

## API Endpoints

| Endpoint | Purpose |
| --- | --- |
| `GET /api/kural?id=1` | Retrieve one Kural by number (1–1330). |
| `GET /api/chapters` | Retrieve chapter metadata. |
| `GET /api/search?q=love` | Search for Kurals by keyword or chapter name. |
| `GET /api/tts?text=...&lang=ta` | Retrieve text-to-speech audio. |

The Kural content is loaded into memory from the local JSON file when the server starts, enabling fast offline lookups. Text-to-speech is the only feature that contacts an external service.

## Project Structure

```text
KuralVerse/
├── ThirukkuralServer.java     # Java HTTP server and REST-style API handlers
├── build_and_run.bat          # Windows launcher
├── build_and_run.sh           # macOS/Linux launcher
├── data/
│   └── thirukkural.json       # Complete Kural dataset
└── public/
    ├── index.html             # Application markup
    ├── style.css              # Responsive UI styles
    ├── script.js              # Client-side behaviour
    └── images/                # Thiruvalluvar artwork
```

## Notes

- Keep the `data` and `public` folders alongside `ThirukkuralServer.java` when running the application.
- Bookmarks are stored only in the current browser via local storage.
- The server runs on port `8080`. Update the `PORT` constant in `ThirukkuralServer.java` if that port is already in use.

## License

No license has been specified for this project. Add a license file before distributing or reusing the code.

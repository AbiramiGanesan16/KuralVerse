# INTERNSHIP PROJECT REPORT ON KURALVERSE: EXPLORE KURAL WITH API POWER USING JAVA AND FRONTEND TECHNOLOGIES

---

## SRM VALLIAMMAI ENGINEERING COLLEGE
### (An Autonomous Institution)
**SRM Nagar, Kattankulathur, Chengalpattu District - 603203, Tamil Nadu.**
### DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING

---

### **INTERNSHIP REPORT**
*Submitted in partial fulfillment of the requirements for the award of the degree of*

**BACHELOR OF ENGINEERING**
*in*
**COMPUTER SCIENCE AND ENGINEERING**

---

**SUBMITTED BY:**  
**STUDENT NAME:** `ABIRAMI G`  
**REGISTER NUMBER:** `142223104001`  

**ORGANIZATION:** `APPROTECH R&D SOLUTIONS PRIVATE LIMITED`  
**INTERNSHIP PERIOD:** `30th June 2026 to 14th July 2026`  

**SEVENTH SEMESTER / ACADEMIC YEAR 2026-2027**

---
---

## CERTIFICATE OF INTERNSHIP COMPLETION

This is to certify that **ABIRAMI G (Reg. No: 142223104001)**, pursuing B.E. in Computer Science and Engineering at **SRM VALLIAMMAI ENGINEERING COLLEGE**, Kattankulathur, has successfully completed an Internship Program in the domain of **Full Stack Development (Java)** at **APPROTECH R&D SOLUTIONS PRIVATE LIMITED** from **30th June 2026 to 14th July 2026**. 

During the internship, the student developed the project titled **"KuralVerse – Explore Kural with API Power Using Java and Frontend Technologies"** (Certificate Ref: **INT:APP26-27/3608-7579**).

<br><br><br>

____________________________________  
**SIGNATURE OF MENTOR**  
**Ms. S. ANSLAM SIBI, M.E., Ph.D.,**  
Assistant Professor (Sr. G),  
Department of CSE,  
SRM Valliammai Engineering College,  
Kattankulathur - 603 203.  

<br><br>

____________________________________  
**SIGNATURE OF HOD**  
**Dr. B. VANATHI, M.E., Ph.D.,**  
Professor & Head,  
Department of CSE,  
SRM Valliammai Engineering College,  
Kattankulathur - 603 203.  

<br><br>

*Submitted for the University examination held on ____________ at SRM Valliammai Engineering College, Kattankulathur.*

---
---

## INDEX

| Sl. No. | Section Title | Page No. |
| :---: | :--- | :---: |
| | **ACKNOWLEDGEMENT** | iv |
| | **VISION & MISSION** | v |
| | **ABSTRACT** | vi |
| | **LIST OF FIGURES** | vii |
| **1** | **INTERNSHIP OVERVIEW & OBJECTIVES** | **1** |
| | 1.1 Internship Overview | 1 |
| | 1.2 Internship Objectives | 2 |
| **2** | **COMPANY PROFILE** | **3** |
| | 2.1 Organization Details: Approtech R&D Solutions Pvt. Ltd. | 3 |
| | 2.2 Key Focus Areas & Technical Expertise | 4 |
| **3** | **INTERNSHIP DESCRIPTION** | **5** |
| | 3.1 Role and Responsibilities | 5 |
| | 3.2 Technologies Used | 6 |
| **4** | **INTERNSHIP PROJECT: KURALVERSE** | **8** |
| | 4.1 Project Title & Introduction | 8 |
| | 4.2 Project Objective | 9 |
| | 4.3 Key Features | 10 |
| | 4.4 Modules Developed | 12 |
| | 4.5 Software and Tools Used | 14 |
| | 4.6 Implementation & Architecture | 15 |
| | 4.7 Annotated Source Code Listing | 18 |
| **5** | **CONCLUSION** | **35** |
| | 5.1 Conclusion & Learning Outcomes | 35 |

---
---

## ACKNOWLEDGEMENT

I wish to express my heartfelt gratitude to the eminent personalities who served as the backbone of my internship's success. I am sincerely thankful to **APPROTECH R&D SOLUTIONS PRIVATE LIMITED** for providing me with the opportunity to undertake this internship in **Full Stack Development (Java)**.

I extend my sincere gratitude to the technical management and mentors at **Approtech R&D Solutions Pvt. Ltd.** for their constant patronage, guidance, and technical insights provided throughout the course of my project development.

We express our deep gratitude to our esteemed Founder Chairman & Chancellor **Dr. T. R. Paarivendhar**, Thiru. **Ravi Pachamoothoo** (Chairman SRM Group & Pro-Chancellor Admin), Mrs. **Padmapriya Ravi** (Vice-Chairman), and Mrs. **R. Harini** (Correspondent, SRM VEC) for the patronage and academic infrastructure provided at **SRM VALLIAMMAI ENGINEERING COLLEGE**.

We thank our respected Principal **Dr. M. Murugan, M.E., Ph.D.**, and Vice-Principal **Dr. Visalakshi S., M.E., Ph.D.**, for the continuous support.

I express my deepest gratitude to my Head of the Department, **Dr. B. Vanathi, M.E., Ph.D.**, for her constructive encouragement. I am immensely thankful to **Ms. S. Anslam Sibi, M.E., Ph.D.**, Assistant Professor (Sr. G), my mentor and Internship Coordinator, Department of Computer Science and Engineering, for her invaluable guidance and supervision.

Finally, I express my extreme thanks to all faculty members, lab staff, and fellow students for their support in making this internship project a success.

---
---

## VISION AND MISSION

### DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING

#### VISION
To devise captivating, fascinating, and unique practices of teaching that discovers the trained talent and inherent competencies of young minds to evolve as humane professional Computer Science Engineers.

#### MISSION
* To provide students with challenging ventures, contributing to the betterment of their selfdom to compete with international talents.
* To act as a motivational hub to exhibit practical knowledge with the latest technological updates and research publications.
* To render ample knowledge to exhibit their ubiquitous talents for the social prosperity and promote industry-institute harmony to upgrade the standards for international reputation.

---
---

## ABSTRACT

The Thirukkural, authored by the ancient Tamil poet-philosopher Thiruvalluvar, stands as a universally acclaimed classical masterpiece comprising 1,330 couplets across 133 chapters. This internship project report presents KuralVerse, a full-stack web application developed during a specialized internship at Approtech R&D Solutions Pvt. Ltd. in the domain of Full Stack Development (Java). Built using Core Java (HttpServer) for the backend and modern HTML5, CSS3, and JavaScript for the frontend, KuralVerse serves static web assets while functioning as an offline-resilient data server and API proxy. By embedding a complete local dataset of all 1,330 Kurals, the system delivers instant, zero-latency couplet lookup with authentic Tamil explanations (குறளின் விளக்கம்) and English translations. Key innovations include a Full Tamil UI mode with an animated canvas particle system of floating Tamil letters, an interactive Book and Chapter Explorer for all 133 Athigarams, global real-time English hover tooltips for cross-lingual accessibility, and a dual-layer Text-to-Speech audio narration engine that combines browser-native Web Speech API with a backend streaming proxy to guarantee reliable voice output on any device.

---
---

## LIST OF FIGURES

| Fig. No. | Caption / Title | Page No. |
| :---: | :--- | :---: |
| **2.1** | Approtech R&D Solutions Pvt. Ltd. Logo & Certification | 3 |
| **4.1** | KuralVerse Client-Server Architecture Diagram | 15 |
| **4.2** | Offline-Resilient Local Dataset & API Fallback Flow | 17 |
| **4.3** | KuralVerse Direct Search Interface (Kural 1) | 30 |
| **4.4** | Kural Search Output for Kural 56 in Full Tamil Mode | 31 |
| **4.5** | Book & Chapter Explorer Interface (133 Athigarams Reader) | 32 |
| **4.6** | Real-Time English Hover Tooltip Tracking Display | 33 |
| **4.7** | Dual-Layer Audio Narration (TTS) Output & Verification | 34 |

---
---

## CHAPTER 1: INTERNSHIP OVERVIEW & OBJECTIVES

### 1.1 INTERNSHIP OVERVIEW
This report details the practical internship completed by **ABIRAMI G (Reg. No: 142223104001)**, a Seventh Semester B.E. Computer Science and Engineering student at **SRM VALLIAMMAI ENGINEERING COLLEGE**. The internship was undertaken at **APPROTECH R&D SOLUTIONS PRIVATE LIMITED** from **30th June 2026 to 14th July 2026** in the domain of **Full Stack Development (Java)** under Certificate Reference **INT:APP26-27/3608-7579**.

During the internship, the student worked as a Full Stack Developer Intern, applying software engineering principles, Java server architecture, REST API design, and modern frontend technologies to build the production-ready web application titled **"KuralVerse – Explore Kural with API Power Using Java and Frontend Technologies"**.

### 1.2 INTERNSHIP OBJECTIVES
The primary technical and professional objectives of the internship were:
1. **Full Stack Java Mastery:** Gain hands-on industrial experience in constructing lightweight, high-performance web servers using Core Java (`com.sun.net.httpserver.HttpServer`) without relying on heavy external frameworks.
2. **API Design & Data Proxying:** Design and implement modular RESTful API endpoints (`/api/kural`, `/api/chapters`, `/api/search`, `/api/tts`) with JSON serialization and CORS support.
3. **Offline Resilience & Data Embedding:** Implement local data caching mechanisms (`data/thirukkural.json`) to guarantee 100% offline uptime and sub-5ms data retrieval speeds.
4. **Culturally Rich UI/UX Engineering:** Develop a Full Tamil Mode interface utilizing CSS3 glassmorphism, responsive grid layouts, custom font typography, and an interactive HTML5 canvas particle subsystem.
5. **Cross-Lingual Accessibility:** Engineer a global cursor-tracking hover tooltip layer (`data-tooltip`) to provide instant English translations over Tamil UI components.
6. **Accessible Audio Narration:** Construct a bulletproof, dual-layer Text-to-Speech (TTS) engine for Tamil audio reading playback.

---
---

## CHAPTER 2: COMPANY PROFILE

### 2.1 ORGANIZATION DETAILS: APPROTECH R&D SOLUTIONS PVT. LTD.
**APPROTECH R&D SOLUTIONS PRIVATE LIMITED** is an ISO 9001:2015 certified, MSME-registered research and development technology company specializing in custom software development, engineering R&D, full-stack software solutions, and technical industrial training.

```
Organization Name : APPROTECH R&D SOLUTIONS PRIVATE LIMITED
Registration      : MSME UDYAM-TN-34-0084298
Certification     : ISO 9001 : 2015 Certified Company
Corporate CIN     : U74900TN2025PTC178688
Address           : No.41/14, Kamarajar Street, West Tambaram, Chennai - 603045.
Contact Numbers   : +91 96265 12344 / +91 96265 44044
Website           : www.approtechsolutions.in
Email             : info@approtechsolutions.in
```

### 2.2 KEY FOCUS AREAS & TECHNICAL EXPERTISE
Approtech R&D Solutions Pvt. Ltd. focuses on:
* **Full Stack Software Development:** Building enterprise Java, Web, and Cloud applications.
* **Research & Development (R&D):** Conducting domain research in embedded systems, web automation, data analytics, and software architecture.
* **Industrial Internships & Training:** Mentoring engineering students through project-based learning and industry-standard coding practices.

---
---

## CHAPTER 3: INTERNSHIP DESCRIPTION

### 3.1 ROLE AND RESPONSIBILITIES
As a **Full Stack Java Developer Intern**, my key responsibilities included:
1. **Backend Server Development:** Implementing `ThirukkuralServer.java` utilizing Java’s `HttpServer` to serve static assets and proxy API calls.
2. **Dataset Management & Integration:** Embedding `data/thirukkural.json` (containing all 1,330 couplets) into server memory for zero-latency lookups.
3. **Frontend UI/UX Design:** Designing `index.html` and `style.css` using modern CSS variables, glassmorphic card elements, and animated backdrops.
4. **Interactive Feature Coding:** Writing `script.js` to handle canvas particle rendering, search event listeners, modal drawers, and bookmarking.
5. **Audio Narration Engine Integration:** Coding dual-layer speech synthesis logic with a backend MP3 streaming handler (`/api/tts`).
6. **Testing & Quality Assurance:** Testing API endpoints, UTF-8 character encoding, cross-browser compatibility, and responsiveness.

### 3.2 TECHNOLOGIES USED
* **Core Java (`com.sun.net.httpserver.HttpServer`):** Multi-threaded web server (`Executors.newFixedThreadPool(12)`).
* **HTML5:** Semantic markup, canvas elements, accessibility tags.
* **CSS3:** Glassmorphism backdrop filters, custom CSS properties, `@keyframes` animations.
* **JavaScript ES6+:** Asynchronous `fetch()`, DOM manipulation, Web Speech API, `localStorage`.
* **JSON:** Local dataset storage (`thirukkural.json`).

---
---

## CHAPTER 4: INTERNSHIP PROJECT: KURALVERSE

### 4.1 PROJECT TITLE & INTRODUCTION
* **Project Title:** *KuralVerse – Explore Kural with API Power Using Java and Frontend Technologies*
* **Introduction:** KuralVerse is a full-stack digital web application developed during the internship to make the 1,330 couplets of Thirukkural accessible through an interactive, culturally authentic, and technologically advanced web interface.

### 4.2 PROJECT OBJECTIVE
To build an offline-resilient, lightweight Java web application capable of serving all 1,330 Kurals instantly with single unified Tamil explanations (**குறளின் விளக்கம்**), English translations, chapter browsing, floating Tamil canvas art, English hover tooltips, and dual-layer audio narration.

### 4.3 KEY FEATURES
1. **Direct Kural Search (1 - 1330):** Instant search bar lookup and "sometime random" couplet generator.
2. **Full Tamil UI Mode:** Authentic Tamil typography with an animated canvas particle system rendering 45 floating Tamil glyphs (`அ`, `ஆ`, `குறள்`, `அறம்`).
3. **Book & Chapter Explorer:** Browse all 3 Paal (*Arathupaal*, *Porutpaal*, *Kamathupaal*) and 133 Athigarams with 10 couplets per chapter.
4. **Single Unified Tamil Meaning ("குறளின் விளக்கம்"):** Displays Dr. Mu. Varadarajan's clear Tamil commentary alongside English translation.
5. **Global English Hover Tooltips (`data-tooltip`):** Hovering over Tamil UI elements displays real-time English translations.
6. **Dual-Layer Audio Narration:** Browser Web Speech API + backend streaming audio proxy (`/api/tts`).
7. **Saved Favorites & Dark Mode:** LocalStorage bookmarks drawer and dark/light theme switcher.

### 4.4 MODULES DEVELOPED
1. **Module 1: Java HttpServer Backend (`ThirukkuralServer.java`)**
2. **Module 2: Frontend Full Tamil UI & Canvas Layer (`index.html` & `style.css`)**
3. **Module 3: Book & Chapter Explorer Mode (`script.js`)**
4. **Module 4: Global English Hover Tooltip Module**
5. **Module 5: Dual-Layer Audio Narration Module**

### 4.5 SOFTWARE AND TOOLS USED
* **Operating System:** Windows 10 / 11
* **Java Development Kit (JDK):** JDK 22 / JDK 11
* **Compiler:** `javac` 22.0.2
* **Code Editor:** VS Code / Gemini IDE
* **Version Control & Utilities:** Git, Python 3.10, PowerShell
* **Web Browsers:** Google Chrome, Microsoft Edge

### 4.6 IMPLEMENTATION & ARCHITECTURE

```
+-----------------------------------------------------------------------+
|                            CLIENT BROWSER                             |
|  +-------------------+  +--------------------+  +------------------+  |
|  | Full Tamil UI     |  | Floating Canvas    |  | Hover Tooltip    |  |
|  | (index.html)      |  | (script.js)        |  | (style.css)      |  |
|  +---------+---------+  +---------+----------+  +--------+---------+  |
+------------|----------------------|----------------------|------------+
             | HTTP GET /           | HTTP GET /api/kural  | HTTP GET /api/tts
             v                      v                      v
+-----------------------------------------------------------------------+
|                       CORE JAVA HTTP SERVER                           |
|                    (ThirukkuralServer.java)                           |
|  +-------------------+  +--------------------+  +------------------+  |
|  | Static File       |  | API Router         |  | TTS Stream       |  |
|  | Handler           |  | (/api/kural)       |  | Handler          |  |
|  +---------+---------+  +---------+----------+  +--------+---------+  |
|            |                      |                      |            |
|            v                      v                      v            |
|      ./public/ Assets    In-Memory Dataset       Google TTS Proxy     |
|      (HTML, CSS, JS)     (data/thirukkural.json) (audio/mpeg)         |
+-----------------------------------------------------------------------+
```

### 4.7 ANNOTATED SOURCE CODE LISTING

#### 4.7.1 `ThirukkuralServer.java`

```java
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * ThirukkuralServer - Core Java Server for KuralVerse Project
 * Developed during Internship at Approtech R&D Solutions Pvt. Ltd.
 */
public class ThirukkuralServer {

    private static final int PORT = 8080;
    private static final String PUBLIC_DIR = "public";
    private static final String DATA_FILE = "data/thirukkural.json";
    private static final Map<Integer, KuralItem> kuralMap = new HashMap<>();

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static void main(String[] args) {
        loadThirukkuralDataset();

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.setExecutor(Executors.newFixedThreadPool(12));

            server.createContext("/", new StaticFileHandler());
            server.createContext("/api/kural", new KuralApiHandler());
            server.createContext("/api/chapters", new ChaptersApiHandler());
            server.createContext("/api/search", new SearchApiHandler());
            server.createContext("/api/tts", new TtsAudioApiHandler());

            server.start();
            System.out.println("KuralVerse Server Started on Port " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class KuralItem {
        int number;
        String line1, line2, mv, mk, translation, explanation;
        KuralItem(int n, String l1, String l2, String mv, String mk, String t, String e) {
            this.number = n; this.line1 = l1; this.line2 = l2;
            this.mv = mv; this.mk = mk; this.translation = t; this.explanation = e;
        }
    }

    private static void loadThirukkuralDataset() {
        Path path = Paths.get(DATA_FILE);
        if (!Files.exists(path)) return;
        try {
            String jsonContent = Files.readString(path, StandardCharsets.UTF_8);
            parseKuralsJson(jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseKuralsJson(String json) {
        int kuralArrStart = json.indexOf("\"kural\":");
        if (kuralArrStart == -1) return;
        int arrOpen = json.indexOf("[", kuralArrStart);
        int arrClose = json.lastIndexOf("]");
        if (arrOpen == -1 || arrClose == -1) return;

        String arrContent = json.substring(arrOpen + 1, arrClose);
        String[] blocks = arrContent.split("\\{\\s*\"Number\":");

        for (String block : blocks) {
            if (block.trim().isEmpty()) continue;
            try {
                int num = extractIntFromBlock(block);
                if (num >= 1 && num <= 1330) {
                    String line1 = extractJsonValue(block, "Line1");
                    String line2 = extractJsonValue(block, "Line2");
                    String mv = extractJsonValue(block, "mv");
                    String mk = extractJsonValue(block, "mk");
                    String translation = extractJsonValue(block, "Translation");
                    if (translation.isEmpty()) translation = extractJsonValue(block, "couplet");
                    String explanation = extractJsonValue(block, "explanation");

                    kuralMap.put(num, new KuralItem(num, line1, line2, mv, mk, translation, explanation));
                }
            } catch (Exception ignored) {}
        }
    }

    private static int extractIntFromBlock(String block) {
        try {
            int endIdx = block.indexOf(",");
            if (endIdx == -1) endIdx = block.indexOf("\n");
            return Integer.parseInt(block.substring(0, endIdx).trim().replaceAll("[^0-9]", ""));
        } catch (Exception e) { return -1; }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String pathStr = exchange.getRequestURI().getPath();
            if (pathStr.equals("/")) pathStr = "/index.html";

            Path filePath = Paths.get(PUBLIC_DIR, pathStr).normalize();
            Path publicBasePath = Paths.get(PUBLIC_DIR).toAbsolutePath().normalize();

            if (!filePath.toAbsolutePath().normalize().startsWith(publicBasePath) || !Files.exists(filePath) || Files.isDirectory(filePath)) {
                sendJsonResponse(exchange, 404, "{\"error\": \"Not Found\"}");
                return;
            }

            byte[] fileBytes = Files.readAllBytes(filePath);
            exchange.getResponseHeaders().set("Content-Type", getMimeType(filePath.toString()));
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, fileBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        }

        private String getMimeType(String path) {
            String lower = path.toLowerCase();
            if (lower.endsWith(".html")) return "text/html; charset=utf-8";
            if (lower.endsWith(".css")) return "text/css; charset=utf-8";
            if (lower.endsWith(".js")) return "application/javascript; charset=utf-8";
            if (lower.endsWith(".json")) return "application/json; charset=utf-8";
            if (lower.endsWith(".png")) return "image/png";
            if (lower.endsWith(".svg")) return "image/svg+xml";
            if (lower.endsWith(".mp3")) return "audio/mpeg";
            return "application/octet-stream";
        }
    }

    static class KuralApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String idStr = queryParams.get("id");
            int kuralId = 1;
            try { if (idStr != null) kuralId = Integer.parseInt(idStr.trim()); } catch (Exception ignored) {}
            if (kuralId < 1 || kuralId > 1330) kuralId = 1;
            sendJsonResponse(exchange, 200, getKuralJsonResponse(kuralId));
        }
    }

    static class ChaptersApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            sendJsonResponse(exchange, 200, getChaptersJson());
        }
    }

    static class SearchApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String query = queryParams.get("q");
            if (query == null || query.trim().isEmpty()) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Query required\"}");
                return;
            }
            sendJsonResponse(exchange, 200, searchKurals(query.trim()));
        }
    }

    static class TtsAudioApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String text = queryParams.get("text");
            String lang = queryParams.getOrDefault("lang", "ta");

            if (text == null || text.trim().isEmpty()) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Text required\"}");
                return;
            }

            try {
                String encodedText = URLEncoder.encode(text.trim(), StandardCharsets.UTF_8);
                String ttsUrl = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" + lang + "&client=tw-ob&q=" + encodedText;

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(ttsUrl))
                        .header("User-Agent", "Mozilla/5.0")
                        .timeout(Duration.ofSeconds(6))
                        .GET()
                        .build();

                HttpResponse<byte[]> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofByteArray());
                if (resp.statusCode() == 200) {
                    exchange.getResponseHeaders().set("Content-Type", "audio/mpeg");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    exchange.sendResponseHeaders(200, resp.body().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.body());
                    os.close();
                } else {
                    sendJsonResponse(exchange, 500, "{\"error\": \"TTS fetch failed\"}");
                }
            } catch (Exception e) {
                sendJsonResponse(exchange, 500, "{\"error\": \"TTS error\"}");
            }
        }
    }

    private static String getKuralJsonResponse(int id) {
        ChapterMeta meta = getChapterMetaForKural(id);
        KuralItem item = kuralMap.get(id);

        String line1 = (item != null) ? item.line1 : "";
        String line2 = (item != null) ? item.line2 : "";
        String mv = (item != null) ? item.mv : "";
        String mk = (item != null && item.mk != null) ? item.mk : mv;
        String translation = (item != null) ? item.translation : "";
        String explanation = (item != null) ? item.explanation : "";

        return String.format(
            "{" +
            "\"number\":%d," +
            "\"sect_tam\":\"%s\"," +
            "\"sect_eng\":\"%s\"," +
            "\"chapgrp_tam\":\"%s\"," +
            "\"chapgrp_eng\":\"%s\"," +
            "\"chap_tam\":\"அதிகாரம் %d: %s\"," +
            "\"chap_eng\":\"%s\"," +
            "\"line1\":\"%s\"," +
            "\"line2\":\"%s\"," +
            "\"tam_exp\":\"%s\"," +
            "\"mk_exp\":\"%s\"," +
            "\"eng\":\"%s\"," +
            "\"eng_exp\":\"%s\"" +
            "}",
            id, escapeJson(meta.paalTam), escapeJson(meta.paalEng),
            escapeJson(meta.iyalTam), escapeJson(meta.iyalEng),
            meta.athigaramNo, escapeJson(meta.athigaramTam), escapeJson(meta.athigaramEng),
            escapeJson(line1), escapeJson(line2), escapeJson(mv), escapeJson(mk),
            escapeJson(translation), escapeJson(explanation)
        );
    }

    private static String searchKurals(String q) {
        List<Integer> matches = new ArrayList<>();
        for (int id = 1; id <= 1330; id++) {
            ChapterMeta meta = getChapterMetaForKural(id);
            if (meta.athigaramTam.contains(q) || String.valueOf(id).equals(q)) {
                matches.add(id);
                if (matches.size() >= 20) break;
            }
        }
        if (matches.isEmpty()) matches.addAll(Arrays.asList(1, 2, 56, 391, 1330));

        StringBuilder sb = new StringBuilder("{\"query\":\"" + escapeJson(q) + "\",\"results\":[");
        for (int i = 0; i < matches.size(); i++) {
            int id = matches.get(i);
            ChapterMeta meta = getChapterMetaForKural(id);
            if (i > 0) sb.append(",");
            sb.append(String.format("{\"number\":%d,\"chap_tam\":\"அதிகாரம் %d: %s\",\"sect_tam\":\"%s\"}",
                    id, meta.athigaramNo, escapeJson(meta.athigaramTam), escapeJson(meta.paalTam)));
        }
        sb.append("]}");
        return sb.toString();
    }

    static class ChapterMeta {
        int athigaramNo;
        String paalTam, paalEng, iyalTam, iyalEng, athigaramTam, athigaramEng;
        ChapterMeta(int no, String pT, String pE, String iyT, String iyE, String aT, String aE) {
            this.athigaramNo = no; this.paalTam = pT; this.paalEng = pE;
            this.iyalTam = iyT; this.iyalEng = iyE; this.athigaramTam = aT; this.athigaramEng = aE;
        }
    }

    private static final String[] ATHIGARAM_NAMES_TAMIL = {
        "கடவுள் வாழ்த்து", "வான் சிறப்பு", "நீத்தார் பெருமை", "அறன் வலியுறுத்தல்",
        "இல்வாழ்க்கை", "வாழ்க்கை துணைநலம்", "புதல்வரைப் பெறுதல்", "அன்புடைமை",
        "விருந்தோம்பல்", "இனியவை கூறல்", "செய்ந்நன்றியறிதல்", "நடுவுநிலைமை",
        "அடக்கமுடைமை", "ஒழுக்கமுடைமை", "பிறனில் விழையாமை", "பொறையுடைமை",
        "அழுக்காறாமை", "வெஃகாமை", "புறங்கூறாமை", "பயனில சொல்லாமை",
        "தீவினைஅச்சம்", "ஒப்புரவறிதல்", "ஈகை", "புகழ்",
        "அருளுடைமை", "புலால் மறுத்தல்", "தவம்", "கூடாவொழுக்கம்",
        "கள்ளாமை", "வாய்மை", "வெகுளாமை", "இன்னா செய்யாமை",
        "கொல்லாமை", "நிலையாமை", "துறவு", "மெய்யுணர்தல்",
        "அவாஅறுத்தல்கள்", "ஊழ்",
        "இறைமாட்சி", "கல்வி", "கல்லாமை", "கேள்வி",
        "அறிவுடைமை", "குற்றங்கடிதல்", "பெரியாரைத் துணைக்கோடல்", "சிற்றினம் அஞ்சல்",
        "தெரிந்துசெயல்வகை", "வலியறிதல்", "காலமறிதல்", "இடனறிதல்",
        "தெரிந்துதெளிதல்", "தெரிந்துவினையாடல்", "சுற்றந்தழால்", "பொச்சாவாமை",
        "செங்கோன்மை", "கொடுங்கோன்மை", "வெருவந்தசெய்யாமை", "கண்ணோட்டம்",
        "ஒற்றாடல்", "ஊக்கமுடைமை", "மடியின்மை", "ஆள்வினையுடைமை",
        "இடுக்கணழியாமை", "அமைச்சு", "பொருள்செயல்வகை", "படைமாட்சி",
        "படைச்செருக்கு", "நட்பு", "நட்பாராய்ந்தல்", "பழைமை",
        "தீநட்பு", "கூடாநட்பு", "பேதைமை", "புல்லறிவாண்மை",
        "இகல்", "பகைமாட்சி", "பகைத்திறந்தெரிதல்", "உட்பகை",
        "பெரியாரைப் பிழையாமை", "பெண்வழிச்சேறல்", "வரைவின்மகளிர்", "கள்ளுண்ணாமை",
        "சூது", "மருந்து", "குடிமை", "மானமுடைமை",
        "பெருமை", "சான்றாண்மை", "பண்புடைமை", "நன்றியில்செல்வம்",
        "நாணுடைமை", "குடிசெயல்வகை", "உழவு", "நல்குரவு",
        "இரவு", "இரவச்சம்", "இன்மை", "கயமை",
        "தகையணங்குறுத்தல்", "குறிப்பறிதல்", "புணர்ச்சிமகிழ்வாதல்", "நலம்புனைந்துரைத்தல்",
        "காதற்சிறப்புரைத்தல்", "நாணுத்துறவுரைத்தல்", "அலரறிவுறுத்தல்", "பிரிவாற்றாமை",
        "படர்மெலிந்திரங்கல்", "கண்விதுப்பழிதல்", "பசலைப்பாய்தல்", "தனிப்படர்மிகுதி",
        "நினைந்தவர் புலம்பல்", "கனவுநிலையுரைத்தல்", "பொழுதுகண்டிரங்கல்", "உறுப்புநலனழிதல்",
        "நெஞ்சொடு கிளத்தல்", "நிறையழிதல்", "அவர்வயின்விதும்பல்", "அமைந்தாங்கமைதல்",
        "புலவி", "புலவி நுணுக்கம்", "ஊடலுவகை"
    };

    private static final String[] ATHIGARAM_NAMES_ENG = {
        "Praise of God", "Blessing of Rain", "Greatness of Ascetics", "Assertion of Virtue",
        "Domestic Life", "Worth of a Wife", "The Possession of Children", "Possession of Love",
        "Hospitality", "Sweet Words", "Gratitude", "Impartiality",
        "Self-Control", "Right Conduct", "Not Coveting Another's Wife", "Forgiveness",
        "Avoidance of Envy", "Avoidance of Greed", "Not Backbiting", "Avoidance of Useless Speech",
        "Dread of Evil Deeds", "Duty to Society", "Charity", "Renown",
        "Compassion", "Abstinence from Flesh", "Penance", "Imposture",
        "Absence of Covetousness", "Truthfulness", "Avoidance of Anger", "Not Doing Harm",
        "Non-Killing", "Instability of Transitory Things", "Renunciation", "Knowledge of Truth",
        "Eradication of Desire", "Destiny",
        "The Greatness of a King", "Learning", "Ignorance", "Listening",
        "Possession of Wisdom", "Correction of Faults", "Seeking Friendship of Great Men", "Avoiding Mean Association",
        "Action Judgment", "Judging Strength", "Knowing Proper Time", "Knowing Proper Place",
        "Testing and Trusting", "Testing and Employing", "Cherishing Kin", "Avoidance of Forgetfulness",
        "Just Rule", "Tyranny", "Avoidance of Terrorizing", "Benignity",
        "Espionage", "Possession of Energy", "Freedom from Sloth", "Manly Effort",
        "Courage in Adversity", "Ministers", "Way of Making Wealth", "Excellence of an Army",
        "Military Spirit", "Friendship", "Testing Friendship", "Familiarity",
        "Bad Friendship", "False Friendship", "Folly", "Ignorance of Truth",
        "Hatred", "Excellence of Enmity", "Estimating Enemy Power", "Internal Enmity",
        "Not Offending the Great", "Being Led by Wife", "Wanton Women", "Avoidance of Drunkenness",
        "Gambling", "Medicine", "Nobility", "Honor", "Greatness", "Perfection", "Courtesy", "Unprofitable Wealth",
        "Modesty", "Advancing the Family", "Agriculture", "Poverty", "Begging", "Dread of Begging", "Meanness", "Baseness",
        "Beauty's Dart", "Deciphering Signs", "Joy of Embracing", "Extolling Beauty", "Declaration of Love's Special Excellence",
        "Renunciating Modesty", "Publishing the Rumor", "Sufferings of Separation", "Complainings of Desolate Sorrow",
        "Eyes Consumed with Grief", "Pallor of Separation", "Solitary Anguish", "Sad Memories", "Dream Visions", "Eventide Lamentation",
        "Wasting Away of Limbs", "Soliloquy with Mind", "Reserve Overthrown", "Longing for Union", "Mutual Adaptability",
        "Feigned Anger", "Subtleties of Feigned Anger", "Delights of Love Quarrel"
    };

    private static ChapterMeta getChapterMetaForKural(int kuralId) {
        int athigaramNo = (kuralId - 1) / 10 + 1;
        if (athigaramNo < 1) athigaramNo = 1;
        if (athigaramNo > 133) athigaramNo = 133;

        String paalTam, paalEng, iyalTam, iyalEng;
        if (athigaramNo <= 38) {
            paalTam = "அறத்துப்பால்"; paalEng = "Arathupaal (Virtue)";
            if (athigaramNo <= 4) { iyalTam = "பாயிரவியல்"; iyalEng = "Prologue"; }
            else if (athigaramNo <= 24) { iyalTam = "இல்லறவியல்"; iyalEng = "Domestic Virtue"; }
            else if (athigaramNo <= 37) { iyalTam = "துறவறவியல்"; iyalEng = "Ascetic Virtue"; }
            else { iyalTam = "ஊழியல்"; iyalEng = "Fate / Destiny"; }
        } else if (athigaramNo <= 108) {
            paalTam = "பொருட்பால்"; paalEng = "Porutpaal (Wealth & Polity)";
            if (athigaramNo <= 63) { iyalTam = "அரசியல்"; iyalEng = "Royalty & Statecraft"; }
            else if (athigaramNo <= 95) { iyalTam = "அங்கவியல்"; iyalEng = "Limb of State"; }
            else { iyalTam = "ஒழிபியல்"; iyalEng = "Miscellaneous Polity"; }
        } else {
            paalTam = "இன்பத்துப்பால்"; paalEng = "Kamathupaal (Love & Delight)";
            if (athigaramNo <= 115) { iyalTam = "களவியல்"; iyalEng = "Secret Love"; }
            else { iyalTam = "கற்பியல்"; iyalEng = "Chaste Wedded Love"; }
        }

        int idx = athigaramNo - 1;
        String aTam = (idx < ATHIGARAM_NAMES_TAMIL.length) ? ATHIGARAM_NAMES_TAMIL[idx] : "அதிகாரம் " + athigaramNo;
        String aEng = (idx < ATHIGARAM_NAMES_ENG.length) ? ATHIGARAM_NAMES_ENG[idx] : "Chapter " + athigaramNo;

        return new ChapterMeta(athigaramNo, paalTam, paalEng, iyalTam, iyalEng, aTam, aEng);
    }

    private static String getChaptersJson() {
        StringBuilder sb = new StringBuilder("{\"paals\":[");
        sb.append("{\"name_tam\":\"அறத்துப்பால்\",\"name_eng\":\"Arathupaal (Virtue)\",\"start_chap\":1,\"end_chap\":38,\"chapters\":[");
        appendChaptersRange(sb, 1, 38);
        sb.append("]},");
        sb.append("{\"name_tam\":\"பொருட்பால்\",\"name_eng\":\"Porutpaal (Wealth)\",\"start_chap\":39,\"end_chap\":108,\"chapters\":[");
        appendChaptersRange(sb, 39, 108);
        sb.append("]},");
        sb.append("{\"name_tam\":\"இன்பத்துப்பால்\",\"name_eng\":\"Kamathupaal (Love)\",\"start_chap\":109,\"end_chap\":133,\"chapters\":[");
        appendChaptersRange(sb, 109, 133);
        sb.append("]}");
        sb.append("]}");
        return sb.toString();
    }

    private static void appendChaptersRange(StringBuilder sb, int start, int end) {
        for (int c = start; c <= end; c++) {
            ChapterMeta meta = getChapterMetaForKural((c - 1) * 10 + 1);
            if (c > start) sb.append(",");
            sb.append(String.format("{\"no\":%d,\"name_tam\":\"அதிகாரம் %d: %s\",\"name_eng\":\"%s\",\"iyal_tam\":\"%s\",\"start_kural\":%d,\"end_kural\":%d}",
                    c, c, escapeJson(meta.athigaramTam), escapeJson(meta.athigaramEng), escapeJson(meta.iyalTam), (c - 1) * 10 + 1, c * 10));
        }
    }

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                try {
                    params.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8), URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
                } catch (Exception ignored) {}
            }
        }
        return params;
    }

    private static String extractJsonValue(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\"";
            int idx = json.indexOf(pattern);
            if (idx == -1) return "";
            int start = idx + pattern.length();
            int end = json.indexOf("\"", start);
            while (end > 0 && json.charAt(end - 1) == '\\') {
                end = json.indexOf("\"", end + 1);
            }
            if (end == -1) return "";
            return json.substring(start, end);
        } catch (Exception e) {
            return "";
        }
    }

    private static String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
```

---
---

## CHAPTER 5: CONCLUSION

### 5.1 CONCLUSION & LEARNING OUTCOMES
The two-week internship program at **APPROTECH R&D SOLUTIONS PRIVATE LIMITED** from **30th June 2026 to 14th July 2026** in the domain of **Full Stack Development (Java)** has been a highly enriching and practical learning experience for **ABIRAMI G (Reg. No: 142223104001)**. 

Through the design and development of the project **"KuralVerse – Explore Kural with API Power Using Java and Frontend Technologies"**, I gained practical exposure in building lightweight Java web servers using `com.sun.net.httpserver.HttpServer`, designing REST APIs, embedding offline datasets, implementing canvas particle graphics, constructing dual-layer Text-to-Speech narration engines, and crafting responsive Tamil digital interfaces. 

This internship reinforced my foundational software engineering skills, prepared me to build industrial-grade web applications, and deepened my confidence to pursue advanced projects in software engineering and web development.

---

## REFERENCES
1. Approtech R&D Solutions Pvt. Ltd., *Internship Training Curriculum & Project Guidelines*, 2026.
2. Thiruvalluvar, *Thirukkural (Classical Tamil Masterpiece)*, ~31 BCE.
3. Dr. Mu. Varadarajan, *Thirukkural Tamil Commentary*, South India Saiva Siddhanta Works Publishing Society.
4. Oracle Java Documentation, `com.sun.net.httpserver.HttpServer` Specification, Java SE 22.
5. MDN Web Docs, *Web Speech API & HTML5 Audio Specification*, Mozilla Developer Network.

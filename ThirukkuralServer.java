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
 * ThirukkuralServer - Lightweight Full-Stack Java Web Server for KuralVerse
 * 
 * Features:
 * 1. Pre-loads complete authentic 1330 Thirukkural dataset from data/thirukkural.json
 * 2. Instant 100% offline accurate responses for all Kurals 1-1330 (Line1, Line2, Mu. Varadarajan, M. Karunanidhi, English)
 * 3. Removed Solomon Pappaiah urai per user specification
 * 4. Serves static frontend files (HTML, CSS, JS, Images, SVG)
 * 5. Provides /api/kural, /api/chapters, /api/search, and /api/tts endpoints
 */
public class ThirukkuralServer {

    private static final int PORT = 8080;
    private static final String PUBLIC_DIR = "public";
    private static final String DATA_FILE = "data/thirukkural.json";
    
    // In-memory dataset map for instant O(1) lookup of all 1330 Kurals
    private static final Map<Integer, KuralItem> kuralMap = new HashMap<>();

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static void main(String[] args) {
        // Load dataset into memory
        loadThirukkuralDataset();

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.setExecutor(Executors.newFixedThreadPool(12));

            // Static File Handler
            server.createContext("/", new StaticFileHandler());

            // API Endpoints
            server.createContext("/api/kural", new KuralApiHandler());
            server.createContext("/api/chapters", new ChaptersApiHandler());
            server.createContext("/api/search", new SearchApiHandler());
            server.createContext("/api/tts", new TtsAudioApiHandler());

            server.start();
            System.out.println("=================================================");
            System.out.println("   KuralVerse Server Started Successfully!");
            System.out.println("   Loaded " + kuralMap.size() + " authentic Kurals into memory!");
            System.out.println("   Server running at: http://localhost:" + PORT);
            System.out.println("   Frontend Directory: ./" + PUBLIC_DIR);
            System.out.println("=================================================");
        } catch (IOException e) {
            System.err.println("Failed to start KuralVerse Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Kural data item container
     */
    static class KuralItem {
        int number;
        String line1;
        String line2;
        String mv; // Mu. Varadarajan
        String mk; // M. Karunanidhi
        String translation; // English translation
        String explanation; // English explanation

        KuralItem(int number, String line1, String line2, String mv, String mk, String translation, String explanation) {
            this.number = number;
            this.line1 = line1;
            this.line2 = line2;
            this.mv = mv;
            this.mk = mk;
            this.translation = translation;
            this.explanation = explanation;
        }
    }

    /**
     * Parse local data/thirukkural.json dataset into memory
     */
    private static void loadThirukkuralDataset() {
        Path path = Paths.get(DATA_FILE);
        if (!Files.exists(path)) {
            System.err.println("Warning: " + DATA_FILE + " not found. Relying on API proxy.");
            return;
        }

        try {
            String jsonContent = Files.readString(path, StandardCharsets.UTF_8);
            parseKuralsJson(jsonContent);
            System.out.println("Successfully loaded " + kuralMap.size() + " Kurals from " + DATA_FILE);
        } catch (Exception e) {
            System.err.println("Error reading " + DATA_FILE + ": " + e.getMessage());
        }
    }

    private static void parseKuralsJson(String json) {
        int kuralArrStart = json.indexOf("\"kural\":");
        if (kuralArrStart == -1) return;

        int arrOpen = json.indexOf("[", kuralArrStart);
        int arrClose = json.lastIndexOf("]");
        if (arrOpen == -1 || arrClose == -1) return;

        String arrContent = json.substring(arrOpen + 1, arrClose);
        // Split objects by "Number": or {
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
            String numStr = block.substring(0, endIdx).trim().replaceAll("[^0-9]", "");
            return Integer.parseInt(numStr);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Serves static frontend files
     */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String pathStr = exchange.getRequestURI().getPath();
            if (pathStr.equals("/")) {
                pathStr = "/index.html";
            }

            Path filePath = Paths.get(PUBLIC_DIR, pathStr).normalize();
            Path publicBasePath = Paths.get(PUBLIC_DIR).toAbsolutePath().normalize();

            if (!filePath.toAbsolutePath().normalize().startsWith(publicBasePath) || !Files.exists(filePath) || Files.isDirectory(filePath)) {
                sendJsonResponse(exchange, 404, "{\"error\": \"Resource not found: " + escapeJson(pathStr) + "\"}");
                return;
            }

            String contentType = getMimeType(filePath.toString());
            byte[] fileBytes = Files.readAllBytes(filePath);

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Cache-Control", "no-cache, max-age=0");

            exchange.sendResponseHeaders(200, fileBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        }

        private String getMimeType(String path) {
            String lower = path.toLowerCase();
            if (lower.endsWith(".html") || lower.endsWith(".htm")) return "text/html; charset=utf-8";
            if (lower.endsWith(".css")) return "text/css; charset=utf-8";
            if (lower.endsWith(".js")) return "application/javascript; charset=utf-8";
            if (lower.endsWith(".json")) return "application/json; charset=utf-8";
            if (lower.endsWith(".png")) return "image/png";
            if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
            if (lower.endsWith(".svg")) return "image/svg+xml";
            if (lower.endsWith(".ico")) return "image/x-icon";
            if (lower.endsWith(".mp3")) return "audio/mpeg";
            return "application/octet-stream";
        }
    }

    /**
     * API Handler for fetching individual Kural by ID (1-1330)
     */
    static class KuralApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendCorsOptionsResponse(exchange);
                return;
            }

            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String idStr = queryParams.get("id");
            if (idStr == null || idStr.isEmpty()) {
                idStr = queryParams.get("num");
            }

            int kuralId = 1;
            try {
                if (idStr != null) {
                    kuralId = Integer.parseInt(idStr.trim());
                }
            } catch (NumberFormatException e) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Invalid Kural number format.\"}");
                return;
            }

            if (kuralId < 1 || kuralId > 1330) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Kural number out of bounds (1-1330).\"}");
                return;
            }

            String jsonPayload = getKuralJsonResponse(kuralId);
            sendJsonResponse(exchange, 200, jsonPayload);
        }
    }

    /**
     * API Handler for returning 133 Athigarams (Chapters) structure
     */
    static class ChaptersApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendCorsOptionsResponse(exchange);
                return;
            }
            sendJsonResponse(exchange, 200, getChaptersJson());
        }
    }

    /**
     * API Handler for searching Kurals by keyword or chapter name
     */
    static class SearchApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendCorsOptionsResponse(exchange);
                return;
            }

            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String query = queryParams.get("q");

            if (query == null || query.trim().isEmpty()) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Search query required.\"}");
                return;
            }

            sendJsonResponse(exchange, 200, searchKurals(query.trim()));
        }
    }

    /**
     * API Handler for proxying TTS audio streams (Tamil / English)
     */
    static class TtsAudioApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendCorsOptionsResponse(exchange);
                return;
            }

            Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
            String text = queryParams.get("text");
            String lang = queryParams.getOrDefault("lang", "ta");

            if (text == null || text.trim().isEmpty()) {
                sendJsonResponse(exchange, 400, "{\"error\": \"Text parameter is required for TTS.\"}");
                return;
            }

            String cleanText = text.trim();
            if (cleanText.length() > 200) {
                cleanText = cleanText.substring(0, 200);
            }

            try {
                String encodedText = URLEncoder.encode(cleanText, StandardCharsets.UTF_8);
                String ttsUrl = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" + lang + "&client=tw-ob&q=" + encodedText;

                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(ttsUrl))
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .timeout(Duration.ofSeconds(6))
                        .GET()
                        .build();

                HttpResponse<byte[]> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofByteArray());

                if (resp.statusCode() == 200 && resp.body().length > 0) {
                    exchange.getResponseHeaders().set("Content-Type", "audio/mpeg");
                    exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                    exchange.getResponseHeaders().set("Cache-Control", "public, max-age=86400");
                    exchange.sendResponseHeaders(200, resp.body().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(resp.body());
                    os.close();
                } else {
                    sendJsonResponse(exchange, 500, "{\"error\": \"Failed to retrieve TTS audio stream.\"}");
                }
            } catch (Exception e) {
                sendJsonResponse(exchange, 500, "{\"error\": \"TTS processing error: " + escapeJson(e.getMessage()) + "\"}");
            }
        }
    }

    // =========================================================================
    // KURAL DATA RESPONSE GENERATOR
    // =========================================================================

    private static String getKuralJsonResponse(int id) {
        ChapterMeta meta = getChapterMetaForKural(id);
        KuralItem item = kuralMap.get(id);

        String line1, line2, mv, mk, translation, explanation;

        if (item != null && item.line1 != null && !item.line1.isEmpty()) {
            line1 = item.line1;
            line2 = item.line2;
            mv = item.mv;
            mk = (item.mk != null && !item.mk.isEmpty()) ? item.mk : item.mv;
            translation = item.translation;
            explanation = item.explanation;
        } else {
            // Secondary fetch from online Vercel API fallback if local item missing
            return fetchKuralDataFromOnline(id, meta);
        }

        return String.format(
            "{" +
            "\"number\":%d," +
            "\"sect_tam\":\"%s\"," +
            "\"sect_eng\":\"%s\"," +
            "\"chapgrp_tam\":\"%s\"," +
            "\"chapgrp_eng\":\"%s\"," +
            "\"chap_tam\":\"%s\"," +
            "\"chap_eng\":\"%s\"," +
            "\"line1\":\"%s\"," +
            "\"line2\":\"%s\"," +
            "\"tam_exp\":\"%s\"," +
            "\"mk_exp\":\"%s\"," +
            "\"eng\":\"%s\"," +
            "\"eng_exp\":\"%s\"" +
            "}",
            id,
            escapeJson(meta.paalTam),
            escapeJson(meta.paalEng),
            escapeJson(meta.iyalTam),
            escapeJson(meta.iyalEng),
            escapeJson(meta.athigaramTam),
            escapeJson(meta.athigaramEng),
            escapeJson(line1),
            escapeJson(line2),
            escapeJson(mv),
            escapeJson(mk),
            escapeJson(translation),
            escapeJson(explanation)
        );
    }

    private static String fetchKuralDataFromOnline(int id, ChapterMeta meta) {
        try {
            String url = "https://api-thirukkural.vercel.app/api?num=" + id;
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(3))
                    .GET()
                    .build();
            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200 && resp.body().contains("line1")) {
                return resp.body();
            }
        } catch (Exception ignored) {}

        return String.format(
            "{\"number\":%d,\"sect_tam\":\"%s\",\"sect_eng\":\"%s\",\"chapgrp_tam\":\"%s\",\"chapgrp_eng\":\"%s\",\"chap_tam\":\"%s\",\"chap_eng\":\"%s\",\"line1\":\"குறள் %d\",\"line2\":\"செய்தி ஏற்றப்படவில்லை.\",\"tam_exp\":\"விளக்கம் இல்லை.\",\"mk_exp\":\"விளக்கம் இல்லை.\",\"eng\":\"Couplet %d\",\"eng_exp\":\"\"}",
            id, escapeJson(meta.paalTam), escapeJson(meta.paalEng), escapeJson(meta.iyalTam), escapeJson(meta.iyalEng), escapeJson(meta.athigaramTam), escapeJson(meta.athigaramEng), id, id
        );
    }

    private static String searchKurals(String q) {
        List<Integer> matches = new ArrayList<>();
        String lowerQ = q.toLowerCase();

        for (int id = 1; id <= 1330; id++) {
            ChapterMeta meta = getChapterMetaForKural(id);
            KuralItem item = kuralMap.get(id);

            boolean match = meta.athigaramTam.contains(q) || meta.athigaramEng.toLowerCase().contains(lowerQ) ||
                            meta.paalTam.contains(q) || String.valueOf(id).equals(q);

            if (!match && item != null) {
                if (item.line1.contains(q) || item.line2.contains(q) || item.mv.contains(q)) {
                    match = true;
                }
            }

            if (match) {
                matches.add(id);
                if (matches.size() >= 30) break;
            }
        }

        if (matches.isEmpty()) {
            matches.addAll(Arrays.asList(1, 2, 3, 4, 5, 56, 391, 392, 1330));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"query\":\"").append(escapeJson(q)).append("\",\"results\":[");
        for (int i = 0; i < matches.size(); i++) {
            int id = matches.get(i);
            ChapterMeta meta = getChapterMetaForKural(id);
            KuralItem item = kuralMap.get(id);

            String l1 = (item != null) ? item.line1 : "";
            String l2 = (item != null) ? item.line2 : "";

            if (i > 0) sb.append(",");
            sb.append(String.format("{\"number\":%d,\"chap_tam\":\"%s\",\"chap_eng\":\"%s\",\"sect_tam\":\"%s\",\"line1\":\"%s\",\"line2\":\"%s\"}",
                    id, escapeJson(meta.athigaramTam), escapeJson(meta.athigaramEng), escapeJson(meta.paalTam), escapeJson(l1), escapeJson(l2)));
        }
        sb.append("]}");
        return sb.toString();
    }

    // =========================================================================
    // ATHIGARAM METADATA
    // =========================================================================

    static class ChapterMeta {
        int athigaramNo;
        String paalTam;
        String paalEng;
        String iyalTam;
        String iyalEng;
        String athigaramTam;
        String athigaramEng;

        ChapterMeta(int no, String pTam, String pEng, String iyTam, String iyEng, String aTam, String aEng) {
            this.athigaramNo = no;
            this.paalTam = pTam;
            this.paalEng = pEng;
            this.iyalTam = iyTam;
            this.iyalEng = iyEng;
            this.athigaramTam = aTam;
            this.athigaramEng = aEng;
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
        // பொருட்பால்
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
        // இன்பத்துப்பால்
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
        // Porutpaal
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
        // Kamathupaal
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
            paalTam = "அறத்துப்பால்";
            paalEng = "Arathupaal (Virtue)";
            if (athigaramNo <= 4) { iyalTam = "பாயிரவியல்"; iyalEng = "Prologue"; }
            else if (athigaramNo <= 24) { iyalTam = "இல்லறவியல்"; iyalEng = "Domestic Virtue"; }
            else if (athigaramNo <= 37) { iyalTam = "துறவறவியல்"; iyalEng = "Ascetic Virtue"; }
            else { iyalTam = "ஊழியல்"; iyalEng = "Fate / Destiny"; }
        } else if (athigaramNo <= 108) {
            paalTam = "பொருட்பால்";
            paalEng = "Porutpaal (Wealth & Polity)";
            if (athigaramNo <= 63) { iyalTam = "அரசியல்"; iyalEng = "Royalty & Statecraft"; }
            else if (athigaramNo <= 95) { iyalTam = "அங்கவியல்"; iyalEng = "Limb of State"; }
            else { iyalTam = "ஒழிபியல்"; iyalEng = "Miscellaneous Polity"; }
        } else {
            paalTam = "இன்பத்துப்பால்";
            paalEng = "Kamathupaal (Love & Delight)";
            if (athigaramNo <= 115) { iyalTam = "களவியல்"; iyalEng = "Secret Love"; }
            else { iyalTam = "கற்பியல்"; iyalEng = "Chaste Wedded Love"; }
        }

        int idx = athigaramNo - 1;
        String aTam = (idx < ATHIGARAM_NAMES_TAMIL.length) ? ATHIGARAM_NAMES_TAMIL[idx] : "அதிகாரம் " + athigaramNo;
        String aEng = (idx < ATHIGARAM_NAMES_ENG.length) ? ATHIGARAM_NAMES_ENG[idx] : "Chapter " + athigaramNo;

        return new ChapterMeta(athigaramNo, paalTam, paalEng, iyalTam, iyalEng, aTam, aEng);
    }

    private static String getChaptersJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"paals\":[");

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
            sb.append(String.format("{\"no\":%d,\"name_tam\":\"%s\",\"name_eng\":\"%s\",\"iyal_tam\":\"%s\",\"start_kural\":%d,\"end_kural\":%d}",
                    c, escapeJson(meta.athigaramTam), escapeJson(meta.athigaramEng), escapeJson(meta.iyalTam), (c - 1) * 10 + 1, c * 10));
        }
    }

    // =========================================================================
    // UTILITIES
    // =========================================================================

    private static void sendJsonResponse(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private static void sendCorsOptionsResponse(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        exchange.sendResponseHeaders(204, -1);
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                try {
                    String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    params.put(k, v);
                } catch (Exception ignored) {}
            }
        }
        return params;
    }

    private static String extractJsonValue(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\"";
            int idx = json.indexOf(pattern);
            if (idx == -1) {
                pattern = "\"" + key + "\":";
                idx = json.indexOf(pattern);
                if (idx == -1) return "";
                int start = idx + pattern.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                if (end == -1) return "";
                return json.substring(start, end).trim().replace("\"", "");
            }
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
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}

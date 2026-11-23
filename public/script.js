/* ==========================================================================
   KuralVerse - Full Stack Application Logic (JavaScript)
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {

    // App State
    let currentKuralId = 1;
    let currentBookPaal = 1;
    let currentBookChapterNo = 1;
    let chaptersData = [];
    let bookmarks = JSON.parse(localStorage.getItem('kural_bookmarks') || '[]');
    let synth = window.speechSynthesis;
    let activeAudioPlayer = null;

    // DOM Elements
    const kuralInput = document.getElementById('kuralInput');
    const searchKuralBtn = document.getElementById('searchKuralBtn');
    const randomKuralBtn = document.getElementById('randomKuralBtn');
    const prevKuralBtn = document.getElementById('prevKuralBtn');
    const nextKuralBtn = document.getElementById('nextKuralBtn');

    const paalBadge = document.getElementById('paalBadge');
    const iyalBadge = document.getElementById('iyalBadge');
    const athigaramBadge = document.getElementById('athigaramBadge');
    const kuralNumDisplay = document.getElementById('kuralNumDisplay');
    const kuralLine1 = document.getElementById('kuralLine1');
    const kuralLine2 = document.getElementById('kuralLine2');

    const expTamilVilakkam = document.getElementById('expTamilVilakkam');
    const expEnglishTrans = document.getElementById('expEnglishTrans');
    const expEnglishDetail = document.getElementById('expEnglishDetail');

    const speakTamilBtn = document.getElementById('speakTamilBtn');
    const speakEnglishBtn = document.getElementById('speakEnglishBtn');
    const bookmarkToggleBtn = document.getElementById('bookmarkToggleBtn');
    const copyKuralBtn = document.getElementById('copyKuralBtn');
    const shareKuralBtn = document.getElementById('shareKuralBtn');

    const themeToggleBtn = document.getElementById('themeToggleBtn');
    const bookmarksModalBtn = document.getElementById('bookmarksModalBtn');
    const bookmarkCount = document.getElementById('bookmarkCount');
    const bookmarksModal = document.getElementById('bookmarksModal');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const bookmarksList = document.getElementById('bookmarksList');
    const toastNotification = document.getElementById('toastNotification');
    const globalHoverTooltip = document.getElementById('globalHoverTooltip');

    // Navigation Mode Switcher Elements
    const navTabs = document.querySelectorAll('.nav-tab');
    const searchModeSection = document.getElementById('searchModeSection');
    const bookModeSection = document.getElementById('bookModeSection');

    // Book Explorer Elements
    const paalTabs = document.querySelectorAll('.paal-tab');
    const chapterSelect = document.getElementById('chapterSelect');
    const currentChapterTitle = document.getElementById('currentChapterTitle');
    const currentChapterRange = document.getElementById('currentChapterRange');
    const chapterKuralsList = document.getElementById('chapterKuralsList');
    const prevChapterBtn = document.getElementById('prevChapterBtn');
    const nextChapterBtn = document.getElementById('nextChapterBtn');

    // =========================================================================
    // 0. GLOBAL FLOATING HOVER TOOLTIP SYSTEM
    // =========================================================================
    function initGlobalHoverTooltips() {
        if (!globalHoverTooltip) return;

        document.addEventListener('mouseover', (e) => {
            const target = e.target.closest('[data-tooltip]');
            if (target) {
                const tooltipText = target.getAttribute('data-tooltip');
                if (tooltipText) {
                    globalHoverTooltip.textContent = tooltipText;
                    globalHoverTooltip.classList.add('visible');
                    positionTooltip(e);
                }
            }
        });

        document.addEventListener('mousemove', (e) => {
            if (globalHoverTooltip.classList.contains('visible')) {
                positionTooltip(e);
            }
        });

        document.addEventListener('mouseout', (e) => {
            const target = e.target.closest('[data-tooltip]');
            if (target) {
                globalHoverTooltip.classList.remove('visible');
            }
        });

        function positionTooltip(e) {
            const padding = 12;
            let x = e.clientX;
            let y = e.clientY - 42;

            const tooltipWidth = globalHoverTooltip.offsetWidth;
            if (x + tooltipWidth / 2 > window.innerWidth - padding) {
                x = window.innerWidth - tooltipWidth / 2 - padding;
            } else if (x - tooltipWidth / 2 < padding) {
                x = tooltipWidth / 2 + padding;
            }

            if (y < padding) {
                y = e.clientY + 24;
            }

            globalHoverTooltip.style.left = `${x - tooltipWidth / 2}px`;
            globalHoverTooltip.style.top = `${y}px`;
        }
    }

    // =========================================================================
    // 1. DYNAMIC FLOATING TAMIL LETTERS CANVAS BACKGROUND
    // =========================================================================
    function initTamilLettersCanvas() {
        const canvas = document.getElementById('tamilLettersCanvas');
        if (!canvas) return;
        const ctx = canvas.getContext('2d');

        let width = (canvas.width = window.innerWidth);
        let height = (canvas.height = window.innerHeight);

        window.addEventListener('resize', () => {
            width = canvas.width = window.innerWidth;
            height = canvas.height = window.innerHeight;
        });

        const tamilGlyphs = [
            'அ', 'ஆ', 'இ', 'ஈ', 'உ', 'ஊ', 'எ', 'ஏ', 'ஐ', 'ஒ', 'ஓ', 'ஔ', 'ஃ',
            'குறள்', 'அறம்', 'பொருள்', 'இன்பம்', 'அன்பு', 'அறிவு', 'ஒழுக்கம்', 'வாய்மை', 'ஈகை', 'புகழ்', 'கல்வி'
        ];

        const particleCount = 45;
        const particles = [];

        for (let i = 0; i < particleCount; i++) {
            particles.push({
                x: Math.random() * width,
                y: Math.random() * height,
                char: tamilGlyphs[Math.floor(Math.random() * tamilGlyphs.length)],
                size: Math.random() * 26 + 18,
                speedY: -(Math.random() * 0.4 + 0.15),
                speedX: Math.sin(Math.random() * Math.PI) * 0.3,
                alpha: Math.random() * 0.45 + 0.15,
                rotation: Math.random() * Math.PI * 2,
                rotSpeed: (Math.random() - 0.5) * 0.008
            });
        }

        function renderCanvas() {
            ctx.clearRect(0, 0, width, height);

            particles.forEach((p) => {
                p.y += p.speedY;
                p.x += p.speedX;
                p.rotation += p.rotSpeed;

                if (p.y < -50) {
                    p.y = height + 50;
                    p.x = Math.random() * width;
                }

                ctx.save();
                ctx.translate(p.x, p.y);
                ctx.rotate(p.rotation);
                ctx.font = `bold ${p.size}px 'Noto Serif Tamil', serif`;
                ctx.fillStyle = `rgba(251, 191, 36, ${p.alpha})`;
                ctx.textAlign = 'center';
                ctx.textBaseline = 'middle';
                ctx.fillText(p.char, 0, 0);
                ctx.restore();
            });

            requestAnimationFrame(renderCanvas);
        }

        renderCanvas();
    }

    // =========================================================================
    // 2. API INTEGRATION - FETCH KURAL DATA
    // =========================================================================
    async function loadKural(id) {
        if (id < 1) id = 1;
        if (id > 1330) id = 1330;
        currentKuralId = id;
        kuralInput.value = id;

        kuralLine1.textContent = "ஏற்றப்படுகிறது...";
        kuralLine2.textContent = "தயவுசெய்து காத்திருக்கவும்.";
        expTamilVilakkam.textContent = "விளக்கம் ஏற்றப்படுகிறது...";

        stopAllSpeech();

        try {
            const response = await fetch(`/api/kural?id=${id}`);
            if (!response.ok) throw new Error('API Response Error');
            const data = await response.json();

            kuralNumDisplay.textContent = data.number || id;
            paalBadge.textContent = data.sect_tam || "அறத்துப்பால்";
            iyalBadge.textContent = data.chapgrp_tam || "பாயிரவியல்";
            
            // Format Chapter Badge as: அதிகாரம் {no}: {name}
            const chapNo = Math.floor((id - 1) / 10) + 1;
            const chapName = data.chap_tam || "கடவுள் வாழ்த்து";
            athigaramBadge.textContent = chapName.startsWith("அதிகாரம்") ? chapName : `அதிகாரம் ${chapNo}: ${chapName}`;

            paalBadge.setAttribute('data-tooltip', `Section: ${data.sect_eng || 'Virtue'}`);
            athigaramBadge.setAttribute('data-tooltip', `Chapter ${chapNo}: ${data.chap_eng || 'Praise of God'}`);

            kuralLine1.textContent = data.line1 || "";
            kuralLine2.textContent = data.line2 || "";

            // Single best Tamil explanation under "குறளின் விளக்கம்"
            expTamilVilakkam.textContent = data.tam_exp || "இக்குறளின் விளக்கம் கிடைக்கப்பெறவில்லை.";
            expEnglishTrans.textContent = data.eng ? `"${data.eng}"` : "";
            expEnglishDetail.textContent = data.eng_exp || "";

            updateBookmarkButtonState();
        } catch (err) {
            console.error('Failed to load Kural:', err);
            kuralLine1.textContent = "குறள் பெற இயலவில்லை.";
            kuralLine2.textContent = "இணைய இணைப்பை சரிபார்க்கவும்.";
        }
    }

    // =========================================================================
    // 3. BOOK EXPLORER & CHAPTERS LOADER
    // =========================================================================
    async function loadChaptersHierarchy() {
        try {
            const resp = await fetch('/api/chapters');
            if (!resp.ok) throw new Error('Failed to load chapters');
            const data = await resp.json();
            chaptersData = data.paals || [];
            renderBookPaal(currentBookPaal);
        } catch (e) {
            console.error('Error fetching chapters hierarchy:', e);
        }
    }

    function renderBookPaal(paalIndex) {
        currentBookPaal = paalIndex;
        if (!chaptersData || chaptersData.length < paalIndex) return;

        const targetPaal = chaptersData[paalIndex - 1];
        chapterSelect.innerHTML = '';

        targetPaal.chapters.forEach((chap) => {
            const opt = document.createElement('option');
            opt.value = chap.no;
            opt.textContent = `அதிகாரம் ${chap.no}: ${chap.name_tam} (${chap.name_eng})`;
            chapterSelect.appendChild(opt);
        });

        currentBookChapterNo = targetPaal.start_chap;
        chapterSelect.value = currentBookChapterNo;
        renderBookChapterKurals(currentBookChapterNo);
    }

    async function renderBookChapterKurals(chapNo) {
        currentBookChapterNo = chapNo;
        chapterSelect.value = chapNo;
        
        let startKural = (chapNo - 1) * 10 + 1;
        let endKural = chapNo * 10;
        
        currentChapterTitle.textContent = `அதிகாரம் ${chapNo}`;
        currentChapterRange.textContent = `குறள்கள் ${startKural} - ${endKural}`;
        chapterKuralsList.innerHTML = '<p style="color: var(--text-gold);">அதிகாரத்தின் 10 குறள்கள் பெறப்படுகின்றன...</p>';

        const items = [];
        for (let i = startKural; i <= endKural; i++) {
            try {
                const res = await fetch(`/api/kural?id=${i}`);
                const d = await res.json();
                items.push(d);
            } catch (e) {
                items.push({ number: i, line1: `குறள் ${i}`, line2: '' });
            }
        }

        if (items.length > 0) {
            const cName = items[0].chap_tam || "";
            const cleanCName = cName.includes(":") ? cName.split(":")[1].trim() : cName;
            currentChapterTitle.textContent = `அதிகாரம் ${chapNo}: ${cleanCName}`;
            currentChapterTitle.setAttribute('data-tooltip', `Chapter ${chapNo}: ${items[0].chap_eng}`);
        }

        let html = '';
        items.forEach((k) => {
            html += `
                <div class="book-kural-item" onclick="openKuralInSearch(${k.number})" data-tooltip="Click to Open Kural ${k.number}">
                    <span class="book-kural-no">குறள் ${k.number}</span>
                    <div class="book-kural-text">${k.line1}<br>${k.line2}</div>
                </div>
            `;
        });
        chapterKuralsList.innerHTML = html;
    }

    window.openKuralInSearch = function(kuralId) {
        switchMode('search');
        loadKural(kuralId);
    };

    // =========================================================================
    // 4. BULLETPROOF TEXT-TO-SPEECH (TTS AUDIO NARRATION)
    // =========================================================================

    function stopAllSpeech() {
        if (synth) {
            synth.cancel();
        }
        if (activeAudioPlayer) {
            activeAudioPlayer.pause();
            activeAudioPlayer = null;
        }
        speakTamilBtn.classList.remove('playing');
        speakEnglishBtn.classList.remove('playing');
    }

    function speakText(text, lang = 'ta', btnElement = speakTamilBtn) {
        stopAllSpeech();
        btnElement.classList.add('playing');

        const cleanText = text.replace(/[^\w\s\u0B80-\u0BFF.,'"]/gi, ' ').trim();
        if (!cleanText) return;

        const targetLangCode = (lang === 'ta') ? 'ta' : 'en';
        const voices = synth ? synth.getVoices() : [];
        const matchingVoice = voices.find(v => v.lang.toLowerCase().startsWith(targetLangCode));

        if (synth && matchingVoice) {
            try {
                const utterance = new SpeechSynthesisUtterance(cleanText);
                utterance.voice = matchingVoice;
                utterance.lang = (lang === 'ta') ? 'ta-IN' : 'en-US';
                utterance.rate = 0.85;

                utterance.onend = () => btnElement.classList.remove('playing');
                utterance.onerror = (err) => {
                    console.warn('SpeechSynthesis error, switching to Audio Stream Fallback:', err);
                    fallbackToAudioApi(cleanText, lang, btnElement);
                };

                synth.speak(utterance);
                return;
            } catch (e) {
                console.warn('SpeechSynthesis exception, using audio stream fallback');
            }
        }

        fallbackToAudioApi(cleanText, lang, btnElement);
    }

    function fallbackToAudioApi(text, lang, btnElement) {
        try {
            const encoded = encodeURIComponent(text);
            const audioUrl = `/api/tts?text=${encoded}&lang=${lang}`;
            
            const audio = new Audio(audioUrl);
            activeAudioPlayer = audio;

            audio.onended = () => {
                btnElement.classList.remove('playing');
                activeAudioPlayer = null;
            };

            audio.onerror = (e) => {
                console.error('Audio TTS stream error:', e);
                btnElement.classList.remove('playing');
                showToast("குரல் உரை இயக்க முடியவில்லை.");
            };

            audio.play().catch(err => {
                console.error('Audio play error:', err);
                btnElement.classList.remove('playing');
            });
        } catch (e) {
            btnElement.classList.remove('playing');
            console.error('Fallback TTS error:', e);
        }
    }

    if (synth && synth.onvoiceschanged !== undefined) {
        synth.onvoiceschanged = () => synth.getVoices();
    }

    // =========================================================================
    // 5. BOOKMARKS & SEARCH HISTORY
    // =========================================================================
    function updateBookmarkButtonState() {
        const isSaved = bookmarks.includes(currentKuralId);
        if (isSaved) {
            bookmarkToggleBtn.classList.add('active');
            bookmarkToggleBtn.querySelector('.action-text').textContent = 'சேமிக்கப்பட்டது';
            bookmarkToggleBtn.setAttribute('data-tooltip', 'Remove from Saved Bookmarks');
        } else {
            bookmarkToggleBtn.classList.remove('active');
            bookmarkToggleBtn.querySelector('.action-text').textContent = 'சேமிக்க';
            bookmarkToggleBtn.setAttribute('data-tooltip', 'Save to Bookmarks');
        }
        bookmarkCount.textContent = bookmarks.length;
    }

    function toggleBookmark() {
        const idx = bookmarks.indexOf(currentKuralId);
        if (idx > -1) {
            bookmarks.splice(idx, 1);
            showToast(`குறள் ${currentKuralId} சேமிப்பகத்திலிருந்து நீக்கப்பட்டது.`);
        } else {
            bookmarks.push(currentKuralId);
            showToast(`குறள் ${currentKuralId} சேமிக்கப்பட்டது! ⭐`);
        }
        localStorage.setItem('kural_bookmarks', JSON.stringify(bookmarks));
        updateBookmarkButtonState();
        renderBookmarksModal();
    }

    function renderBookmarksModal() {
        if (bookmarks.length === 0) {
            bookmarksList.innerHTML = '<p style="color: var(--text-secondary); text-align: center;">இன்னும் குறள்கள் எதுவும் சேமிக்கப்படவில்லை.</p>';
            return;
        }

        bookmarksList.innerHTML = '';
        bookmarks.forEach(id => {
            const li = document.createElement('li');
            li.className = 'bookmark-item';
            li.setAttribute('data-tooltip', `Read Saved Kural ${id}`);
            li.innerHTML = `
                <div>
                    <strong style="color: var(--text-gold);">குறள் ${id} (Kural ${id})</strong>
                </div>
                <button class="btn-secondary" style="padding: 4px 10px; font-size: 0.85rem;" onclick="openKuralInSearch(${id}); closeModal();" data-tooltip="Open Kural ${id}">வாசிக்க ➡️</button>
            `;
            bookmarksList.appendChild(li);
        });
    }

    window.closeModal = function() {
        bookmarksModal.classList.remove('active');
    };

    // =========================================================================
    // 6. UI UTILITIES (CLIPBOARD, SHARE, TOAST)
    // =========================================================================
    function copyKuralToClipboard() {
        const textToCopy = `திருக்குறள் ${currentKuralId}:\n${kuralLine1.textContent}\n${kuralLine2.textContent}\n\nவிளக்கம்: ${expTamilVilakkam.textContent}\n- குறள்வேர்ஸ் (KuralVerse)`;
        navigator.clipboard.writeText(textToCopy).then(() => {
            showToast("குறள் பிரதி செய்யப்பட்டது! 📋");
        }).catch(() => {
            showToast("பிரதி செய்ய இயலவில்லை.");
        });
    }

    function shareKural() {
        const shareData = {
            title: `திருக்குறள் ${currentKuralId}`,
            text: `${kuralLine1.textContent}\n${kuralLine2.textContent}\n- குறள்வேர்ஸ்`,
            url: window.location.href
        };

        if (navigator.share) {
            navigator.share(shareData).catch(() => {});
        } else {
            copyKuralToClipboard();
        }
    }

    function showToast(message) {
        toastNotification.textContent = message;
        toastNotification.classList.add('show');
        setTimeout(() => toastNotification.classList.remove('show'), 2500);
    }

    function switchMode(mode) {
        navTabs.forEach(tab => {
            if (tab.getAttribute('data-mode') === mode) {
                tab.classList.add('active');
            } else {
                tab.classList.remove('active');
            }
        });

        if (mode === 'search') {
            searchModeSection.classList.add('active');
            bookModeSection.classList.remove('active');
        } else {
            searchModeSection.classList.remove('active');
            bookModeSection.classList.add('active');
        }
    }

    // =========================================================================
    // 7. EVENT LISTENERS
    // =========================================================================

    navTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const mode = tab.getAttribute('data-mode');
            switchMode(mode);
        });
    });

    searchKuralBtn.addEventListener('click', () => {
        const val = parseInt(kuralInput.value);
        if (val >= 1 && val <= 1330) {
            loadKural(val);
        } else {
            showToast("1 முதல் 1330 வரை உள்ள எண்ணை உள்ளிடுக.");
        }
    });

    kuralInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            searchKuralBtn.click();
        }
    });

    randomKuralBtn.addEventListener('click', () => {
        const randomId = Math.floor(Math.random() * 1330) + 1;
        loadKural(randomId);
    });

    prevKuralBtn.addEventListener('click', () => {
        if (currentKuralId > 1) loadKural(currentKuralId - 1);
    });

    nextKuralBtn.addEventListener('click', () => {
        if (currentKuralId < 1330) loadKural(currentKuralId + 1);
    });

    document.querySelectorAll('.paal-pill').forEach(pill => {
        pill.addEventListener('click', () => {
            const kNo = parseInt(pill.getAttribute('data-kural'));
            loadKural(kNo);
        });
    });

    speakTamilBtn.addEventListener('click', () => {
        const textToRead = `${kuralLine1.textContent}. ${kuralLine2.textContent}. ${expTamilVilakkam.textContent}`;
        speakText(textToRead, 'ta', speakTamilBtn);
    });

    speakEnglishBtn.addEventListener('click', () => {
        const textToRead = `${expEnglishTrans.textContent}. ${expEnglishDetail.textContent}`;
        speakText(textToRead, 'en', speakEnglishBtn);
    });

    bookmarkToggleBtn.addEventListener('click', toggleBookmark);
    copyKuralBtn.addEventListener('click', copyKuralToClipboard);
    shareKuralBtn.addEventListener('click', shareKural);

    paalTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            paalTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            const paalNo = parseInt(tab.getAttribute('data-paal'));
            renderBookPaal(paalNo);
        });
    });

    chapterSelect.addEventListener('change', (e) => {
        const chapNo = parseInt(e.target.value);
        renderBookChapterKurals(chapNo);
    });

    prevChapterBtn.addEventListener('click', () => {
        if (currentBookChapterNo > 1) {
            renderBookChapterKurals(currentBookChapterNo - 1);
        }
    });

    nextChapterBtn.addEventListener('click', () => {
        if (currentBookChapterNo < 133) {
            renderBookChapterKurals(currentBookChapterNo + 1);
        }
    });

    themeToggleBtn.addEventListener('click', () => {
        document.body.classList.toggle('light-mode');
        const isLight = document.body.classList.contains('light-mode');
        themeToggleBtn.querySelector('.icon').textContent = isLight ? '☀️' : '🌙';
    });

    bookmarksModalBtn.addEventListener('click', () => {
        renderBookmarksModal();
        bookmarksModal.classList.add('active');
    });

    closeModalBtn.addEventListener('click', closeModal);
    bookmarksModal.addEventListener('click', (e) => {
        if (e.target === bookmarksModal) closeModal();
    });

    document.addEventListener('keydown', (e) => {
        if (document.activeElement === kuralInput) return;
        if (e.key === 'ArrowLeft') prevKuralBtn.click();
        if (e.key === 'ArrowRight') nextKuralBtn.click();
    });

    initGlobalHoverTooltips();
    initTamilLettersCanvas();
    loadKural(1);
    loadChaptersHierarchy();
    updateBookmarkButtonState();
});

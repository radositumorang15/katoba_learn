import SwiftUI
import ComposeApp
import AVFoundation

class SwiftAudioSpeaker: NSObject, NativeAudioSpeaker, AVSpeechSynthesizerDelegate {
    static let shared = SwiftAudioSpeaker()
    private let synthesizer = AVSpeechSynthesizer()

    override init() {
        super.init()
        synthesizer.delegate = self
    }

    func speak(text: String, lang: String) {
        let trimmed = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmed.isEmpty else { return }

        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }

            if self.synthesizer.isSpeaking {
                self.synthesizer.stopSpeaking(at: .immediate)
            }

            let utterance = AVSpeechUtterance(string: trimmed)
            let voiceCode = (lang == "ja") ? "ja-JP" : "en-US"

            if let voice = AVSpeechSynthesisVoice(language: voiceCode) {
                utterance.voice = voice
            }

            utterance.rate = 0.46
            utterance.volume = 1.0
            utterance.pitchMultiplier = 1.0

            self.synthesizer.speak(utterance)
            NSLog("🔊 [SwiftAudioSpeaker] Speaking: '%@' (lang=%@)", trimmed, voiceCode)
        }
    }

    func stop() {
        DispatchQueue.main.async { [weak self] in
            if self?.synthesizer.isSpeaking == true {
                self?.synthesizer.stopSpeaking(at: .immediate)
            }
        }
    }
}

@main
struct iOSApp: App {
    init() {
        NSLog("🚀 [KOTOBA] iOSApp.init() started")

        // 1. Tangkap Objective-C / Swift Uncaught Exception dengan NSLog (muncul di iPhone syslog)
        NSSetUncaughtExceptionHandler { exception in
            NSLog("🚨 [FATAL EXCEPTION] Name: %@", exception.name.rawValue)
            NSLog("🚨 [FATAL EXCEPTION] Reason: %@", exception.reason ?? "No reason")
            NSLog("🚨 [FATAL EXCEPTION] CallStack:\n%@", exception.callStackSymbols.joined(separator: "\n"))
        }

        // 2. Konfigurasi AVAudioSession ke .playback dengan mode .spokenAudio agar vokal TTS jernih & lantang walau iPhone dalam mode Silent/Hening
        do {
            let session = AVAudioSession.sharedInstance()
            try session.setCategory(.playback, mode: .spokenAudio, options: [.duckOthers])
            try session.setActive(true)
            NSLog("🔊 [KOTOBA] AVAudioSession configured to .playback with mode .spokenAudio")
        } catch {
            NSLog("⚠️ [KOTOBA] Failed to configure AVAudioSession: %@", error.localizedDescription)
        }

        // 3. Daftarkan native Swift audio speaker ke NativeAudioHub
        NativeAudioHub.shared.speaker = SwiftAudioSpeaker.shared
        NSLog("✅ [KOTOBA] NativeAudioHub speaker registered")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

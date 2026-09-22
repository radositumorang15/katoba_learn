import SwiftUI
import ComposeApp
import AVFoundation

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

        // 2. Tangkap sinyal Unix Abort sebelum proses mati
        signal(SIGABRT) { sig in
            NSLog("🚨 [FATAL SIGNAL] Caught SIGABRT (%d)! App terminating.", sig)
        }
        signal(SIGILL) { sig in
            NSLog("🚨 [FATAL SIGNAL] Caught SIGILL (%d)! App terminating.", sig)
        }

        // 3. Konfigurasi AVAudioSession ke .playback dengan mode .spokenAudio agar vokal TTS jernih & lantang walau iPhone dalam mode Silent/Hening
        do {
            let session = AVAudioSession.sharedInstance()
            try session.setCategory(.playback, mode: .spokenAudio, options: [.duckOthers])
            try session.setActive(true)
            NSLog("🔊 [KOTOBA] AVAudioSession configured to .playback with mode .spokenAudio")
        } catch {
            NSLog("⚠️ [KOTOBA] Failed to configure AVAudioSession: %@", error.localizedDescription)
        }

        NSLog("✅ [KOTOBA] Crash handlers and audio session installed")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

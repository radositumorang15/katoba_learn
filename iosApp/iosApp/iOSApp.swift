import SwiftUI
import ComposeApp

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

        NSLog("✅ [KOTOBA] Crash handlers and signals installed")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

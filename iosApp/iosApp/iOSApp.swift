import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        // 1. Tangkap Objective-C / Swift Uncaught Exception
        NSSetUncaughtExceptionHandler { exception in
            print("🚨 [FATAL EXCEPTION] Name: \(exception.name.rawValue)")
            print("🚨 [FATAL EXCEPTION] Reason: \(exception.reason ?? "No reason")")
            print("🚨 [FATAL EXCEPTION] CallStack:\n\(exception.callStackSymbols.joined(separator: "\n"))")
        }

        // 2. Tangkap sinyal Unix Abort sebelum proses mati
        signal(SIGABRT) { sig in
            print("🚨 [FATAL SIGNAL] Caught SIGABRT (\(sig))! App will terminate.")
        }
        signal(SIGILL) { sig in
            print("🚨 [FATAL SIGNAL] Caught SIGILL (\(sig))! App will terminate.")
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

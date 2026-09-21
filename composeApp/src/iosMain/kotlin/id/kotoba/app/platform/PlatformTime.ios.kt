package id.kotoba.app.platform

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun getPlatformTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000.0).toLong()
}

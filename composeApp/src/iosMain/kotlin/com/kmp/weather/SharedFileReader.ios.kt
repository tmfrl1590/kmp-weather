package com.kmp.weather

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSBundle
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.darwin.NSObject
import platform.darwin.NSObjectMeta

internal actual class SharedFileReader {
    private val bundle: NSBundle = NSBundle.bundleForClass(BundleMarker)

    @OptIn(ExperimentalForeignApi::class)
    actual fun loadFile(fileName: String): String {
        val fff = "/compose-resources/assets/reduced_citylist.json"

        val (filename, type) = when (val lastPeriodIndex = fff.lastIndexOf('.')) {
            0 -> {
                null to fff.drop(1)
            }
            in 1..Int.MAX_VALUE -> {
                fff.take(lastPeriodIndex) to fff.drop(lastPeriodIndex + 1)
            }
            else -> {
                fff to null
            }
        }
        val path = bundle.pathForResource(filename, type) ?: error("Couldn't get path of $fff (parsed as: ${listOfNotNull(filename, type).joinToString(".")})")

        return memScoped {
            val errorPtr = alloc<ObjCObjectVar<NSError?>>()

            NSString.stringWithContentsOfFile(path, encoding = NSUTF8StringEncoding, error = errorPtr.ptr) ?: run {
                error("Couldn't load resource: $fff. Error: ${errorPtr.value?.localizedDescription}")
            }
        }
    }

    private class BundleMarker : NSObject() {
        companion object : NSObjectMeta()
    }
}
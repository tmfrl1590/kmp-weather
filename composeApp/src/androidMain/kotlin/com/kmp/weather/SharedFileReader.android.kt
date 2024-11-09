package com.kmp.weather

import java.io.InputStreamReader

internal actual class SharedFileReader {
    actual fun loadFile(fileName: String): String {
        val androidFilePath = "assets/reduced_citylist.json"
        return javaClass.classLoader?.getResourceAsStream(androidFilePath).use { stream ->
            InputStreamReader(stream).use { reader ->
                reader.readText()
            }
        }
    }
}
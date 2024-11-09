package com.kmp.weather

internal expect class SharedFileReader() {
    fun loadFile(fileName: String): String
}
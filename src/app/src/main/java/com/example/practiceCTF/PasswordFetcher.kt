package com.example.practiceCTF

import kotlin.random.Random

class PasswordFetcher(key: Int) {
    private var random: Random
    private val originKey: Int

    init {
        random = Random(key)
        originKey = key
    }

    val password: String
        get() {
            val sb = StringBuilder()
            for (i in 0..12) {
                val randomChar = ALPHABET[random.nextInt(ALPHABET.length)]
                sb.append(randomChar)
            }
            random = Random(originKey)
            return sb.toString()
        }

    companion object {
        private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz1234567890"
    }
}

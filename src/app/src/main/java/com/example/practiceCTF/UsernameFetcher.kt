package com.example.practiceCTF

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES Encrypted Username.
 */
class UsernameFetcher {

    private val ENCRYPTED_USERNAME: String = "oaryGaWV4Fe8YQzd9w6jKJxK5WA="
    private val USERNAME_KEY: String = "Pancakes"

    private val AES_MODE = "AES/CTR/NoPadding"
    private val KEY_SIZE = 128
    private val ITERATIONS = 10000

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUsername(): String? {
        return decrypt(ENCRYPTED_USERNAME, USERNAME_KEY)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decrypt(encryptedInput: String, key: String): String? = try {
        val combinedBytes = Base64.getDecoder().decode(encryptedInput)

        val ivBytes = ByteArray(16)
        System.arraycopy(combinedBytes, 0, ivBytes, 0, ivBytes.size)
        val iv = IvParameterSpec(ivBytes)

        val encryptedBytes = ByteArray(combinedBytes.size - ivBytes.size)
        System.arraycopy(combinedBytes, ivBytes.size, encryptedBytes, 0, encryptedBytes.size)

        val secretKey = generateKey(key)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

        val decryptedBytes = cipher.doFinal(encryptedBytes)

        String(decryptedBytes, Charsets.UTF_8)
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }

    private fun generateKey(key: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec: KeySpec = PBEKeySpec(key.toCharArray(), ByteArray(16), ITERATIONS, KEY_SIZE)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, "AES")
    }
}



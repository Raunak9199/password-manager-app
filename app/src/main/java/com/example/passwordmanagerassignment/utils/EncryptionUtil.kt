package com.example.passwordmanagerassignment.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object EncryptionUtil {

    private const val KEY_ALIAS = "password_manager_key"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val ENCRYPTION_BLOCK_SIZE = 128

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        return if (keyStore.containsAlias(KEY_ALIAS)) {
            val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            secretKeyEntry.secretKey
        } else {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(ENCRYPTION_BLOCK_SIZE).build()
            )
            keyGenerator.generateKey()
        }
    }

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())
        return iv.joinToString(separator = ",") + ":" + encryptedData.joinToString(separator = ",")
    }

    fun decrypt(encryptedData: String): String {
        val parts = encryptedData.split(":")
        val iv = parts[0].split(",").map { it.toByte() }.toByteArray()
        val encryptedBytes = parts[1].split(",").map { it.toByte() }.toByteArray()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(ENCRYPTION_BLOCK_SIZE, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        return String(cipher.doFinal(encryptedBytes))
    }
}

package com.example.prashanth.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*

private const val FILE_NAME = "SharedPrefs"
private const val KEY_SIZE = "KeySize"
private const val SHARE_KEY_FORMAT = "OBJ%d"

class SharedRepositoryImpl(context: Context) : SharedRepository {

    override fun storeJson(jsonString: String): String {
        val key = getKey()
        sharedPreference.edit { it.putString(key, jsonString) }
        return key
    }

    override fun fetchJson(key: String) =
        sharedPreference.getString(key, "").orEmpty()

    override fun clear() {
        sharedPreference.edit().clear().apply()
    }

    override fun updateKey(key: String, value: String) {
        sharedPreference.edit { it.putString(key, value) }
    }

    override fun removeKey(key: String) {
        sharedPreference.edit().remove(key).apply()
    }

    private fun getKey(): String {
        val currentSize = sharedPreference.getInt(KEY_SIZE, 0)
        sharedPreference.edit { it.putInt(KEY_SIZE, currentSize + 1) }

        return String.format(Locale.US, SHARE_KEY_FORMAT, currentSize)
    }

    private val sharedPreference by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        EncryptedSharedPreferences
            .create(
                FILE_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

}


private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

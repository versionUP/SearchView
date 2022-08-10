package com.example.prashanth.repository

interface SharedRepository {

    /**
     * Store json string to repository.
     *
     * @param jsonString The json string to be stored.
     * @return Returns key for fetch json string.
     */
    fun storeJson(jsonString: String): String

    /**
     * Fetch json string from repository by key.
     *
     * @param key The name for lookup related json string.
     * @return Returns stored json string.
     */
    fun fetchJson(key: String): String

    /**
     * Clear repository stored data.
     */
    fun clear()

    /**
     * Updates Existing key value.
     */
    fun updateKey(key: String, value: String)

    /**
     * Removes the provided key from stored data
     */
    fun removeKey(key: String)
}

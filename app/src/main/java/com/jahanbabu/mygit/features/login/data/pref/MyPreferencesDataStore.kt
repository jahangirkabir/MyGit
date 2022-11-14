package com.jahanbabu.mygit.features.login.data.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreferencesDataStore @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : PreferencesDataStore {

    override suspend fun setRefreshedToken(
        refreshedToken: String
    ) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[REFRESHED_TOKEN] = refreshedToken
            }
        }
    }

    override suspend fun getRefreshedToken(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to "" if not set
                    preferences[REFRESHED_TOKEN]
                }
            val value = flow.firstOrNull() ?: "" // we only care about the 1st value
            value
        }
    }

    override suspend fun setAccessToken(
        accessToken: String
    ) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[ACCESS_TOKEN] = accessToken
            }
        }
    }

    override suspend fun getAccessToken(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to "" if not set
                    preferences[ACCESS_TOKEN]
                }
            val value = flow.firstOrNull() ?: "" // we only care about the 1st value
            value
        }
    }


    override suspend fun setAccessTokenCreateTime(
        tokenCreateTime: Long
    ) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[TOKEN_CREATE_TIME] = tokenCreateTime
            }
        }
    }


    override suspend fun getTokenCreateTime(): Result<Long> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to "" if not set
                    preferences[TOKEN_CREATE_TIME]
                }
            val value = flow.firstOrNull() ?: 0 // we only care about the 1st value
            value
        }
    }


    private companion object {

        val REFRESHED_TOKEN = stringPreferencesKey(
            name = "refreshed_token"
        )

        val ACCESS_TOKEN = stringPreferencesKey(
            name = "access_token"
        )

        val TOKEN_CREATE_TIME = longPreferencesKey(
            name = "create_time"
        )
    }
}
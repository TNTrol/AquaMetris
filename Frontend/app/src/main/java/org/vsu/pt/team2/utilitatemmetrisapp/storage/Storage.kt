package org.vsu.pt.team2.utilitatemmetrisapp.storage

import android.content.SharedPreferences
import org.vsu.pt.team2.utilitatemmetrisapp.models.User
import javax.inject.Inject

class Storage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUser(user: User, isDemo: Boolean) {
        sharedPreferences.edit()
            .putString(KEY_USER_EMAIL, user.email)
            .putString(KEY_USER_JWT, user.token)
            .putBoolean(KEY_USER_DEMO, isDemo)
            .apply()
    }

    fun clearUser() {
        sharedPreferences.edit()
            .remove(KEY_USER_EMAIL)
            .remove(KEY_USER_JWT)
            .remove(KEY_USER_DEMO)
            .apply()
    }

    fun getUser(): Pair<User, Boolean>? {
        User(
            sharedPreferences.getString(KEY_USER_EMAIL, null) ?: return null,
            sharedPreferences.getString(KEY_USER_JWT, null) ?: return null,
        ).also {
            return Pair(it, sharedPreferences.getBoolean(KEY_USER_DEMO, true))
        }
    }

}
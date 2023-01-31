package com.udacity.shoestore.data

import android.content.Context
import android.content.SharedPreferences
import com.udacity.shoestore.domain.UserRepository
import com.udacity.shoestore.domain.models.User


class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val mySharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    var isUserSignedIn = false

    override fun signUp(user: User): Boolean {
        mySharedPreferences.edit()
            .putString(REGISTERED_USER_LOGIN, user.login)
            .putString(REGISTERED_USER_PASSWORD, user.password)
            .apply()

        return signIn(user)
    }

    override fun signIn(user: User): Boolean {
        val currentLogin = mySharedPreferences.getString(REGISTERED_USER_LOGIN, "")
        val currentPassword = mySharedPreferences.getString(REGISTERED_USER_PASSWORD, "")

        return if (user.login == currentLogin && user.password == currentPassword) {
            isUserSignedIn = true
            true
        } else {
            false
        }
    }

    override fun logOut() {
        isUserSignedIn = false
    }

    companion object {
        private const val REGISTERED_USER_LOGIN = "REGISTERED_USER_LOGIN"
        private const val REGISTERED_USER_PASSWORD = "REGISTERED_USER_PASSWORD"
        private const val APP_PREFERENCES = "shoe_store_settings"
    }
}


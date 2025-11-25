package com.example.zappay.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveLoginState(isLoggedIn: Boolean, userRut: String = "") {
        prefs.edit().apply {
            putBoolean("is_logged_in", isLoggedIn) // guarda si está logueado (true/false) se vee en la linea 18
            putString("user_rut", userRut) //guarda el run del user
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false) // aca vee si la sesión esta activa
    }

    fun getLoggedUserRut(): String {
        return prefs.getString("user_rut", "") ?: ""
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
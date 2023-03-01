package id.heycoding.sportstesiyen.utils

import android.content.Context
import android.preference.PreferenceManager

class PreferencesApp(context: Context) {
    companion object {
        private const val DARK_STATUS = "dark_status"
    }

    private val preferencesApp = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferencesApp.getInt(DARK_STATUS, 0)
        set(value) = preferencesApp.edit().putInt(DARK_STATUS, value).apply()
}
package os.wear.pulse.data.fileSystem

import android.content.Context
import androidx.preference.PreferenceManager


class SharedPreferencesManager(private val context: Context) {

    companion object {
        const val INTENSITY_DURATION_TYPE = "intensity_duration_type"
    }

    fun save(key: String, value: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntValue(key: String): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getInt(key, -1)
    }
}
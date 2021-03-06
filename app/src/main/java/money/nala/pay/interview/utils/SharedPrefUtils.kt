package money.nala.pay.interview.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(
        val sharedPreferences: SharedPreferences,
        private val key: String,
        private val defValue: Boolean
) : LiveData<T>() {

    private val onSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == this.key) {
                    value = getValueFromSharedPreferences(key, defValue)
                }
            }

    abstract fun getValueFromSharedPreferences(key: String, defValue: Boolean): T

    override fun onActive() {
        super.onActive()
        value = getValueFromSharedPreferences(key, defValue)
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                onSharedPreferenceChangeListener
        )
        super.onInactive()
    }
}

class BalanceSharedPreferenceLiveData(
        sharedPreferences: SharedPreferences,
        key: String,
        defValue: Boolean
) : SharedPreferenceLiveData<Boolean>(sharedPreferences, key, defValue) {

    override fun getValueFromSharedPreferences(key: String, defValue: Boolean): Boolean =
            sharedPreferences.getBoolean(key, defValue)
}

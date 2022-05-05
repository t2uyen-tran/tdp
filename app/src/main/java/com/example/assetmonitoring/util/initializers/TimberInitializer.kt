package com.example.assetmonitoring.util.initializers

import android.content.Context
import androidx.startup.Initializer
import com.example.assetmonitoring.BuildConfig
import com.example.assetmonitoring.util.CrashlyticsTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
package ru.chieffly.memoscope

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.chieffly.memoscope.di.AppComponent
import ru.chieffly.memoscope.di.AppModule
import ru.chieffly.memoscope.di.DaggerAppComponent

class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null
        lateinit var appComponent: AppComponent

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
                appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this@MyApp))
            .build()
        super.onCreate()

    }


}
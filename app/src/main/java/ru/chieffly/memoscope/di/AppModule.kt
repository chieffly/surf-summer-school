package ru.chieffly.memoscope.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.db.AppDatabase
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.utils.UserStorage
import javax.inject.Singleton

@Module
class AppModule(private val myApp: MyApp) {
    private  var app: MyApp = myApp

    @Provides @Singleton
    fun provideApp(): MyApp = app

    @Provides @Singleton
    fun provideContext(): Context = app

    @Provides @Singleton
    fun provideStorage(context: Context): UserStorage =
        UserStorage(context)

    @Provides @Singleton
    fun providesMeetDao(memDb: AppDatabase) : MemDao = memDb.memDao()

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences = app.getSharedPreferences(app.getString(R.string.app_name), 0)

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context,
                AppDatabase::class.java,
                "base").build()
}
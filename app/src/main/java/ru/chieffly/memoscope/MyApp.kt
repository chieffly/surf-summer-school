package ru.chieffly.memoscope

import android.app.Application
import android.content.Context
import ru.chieffly.memoscope.db.AppDatabase
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.utils.UserStorage

class MyApp : Application() {
    private lateinit var memDB : MemDao
    private lateinit var storage : UserStorage
    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        storage = UserStorage(this.applicationContext)
        memDB = AppDatabase.getDatabase(this.applicationContext).memDao()

    }

    fun getDB(): MemDao {
        return  memDB
    }

    fun getStorage() : UserStorage
    {
        return storage
    }
}
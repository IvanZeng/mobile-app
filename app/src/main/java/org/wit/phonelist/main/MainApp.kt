package org.wit.phonelist.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.phonelist.models.PhonelistJSONStore
import org.wit.phonelist.models.PhonelistStore

class MainApp : Application(), AnkoLogger {

  lateinit var phonelists: PhonelistStore

  override fun onCreate() {
    super.onCreate()
    phonelists = PhonelistJSONStore(applicationContext)
    info("Phonelist started")
  }
}
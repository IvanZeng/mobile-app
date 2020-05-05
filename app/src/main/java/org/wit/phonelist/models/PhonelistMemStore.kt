package org.wit.phonelist.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class PhonelistMemStore : PhonelistStore, AnkoLogger {

  val phonelists = ArrayList<PhonelistModel>()

  override fun findAll(): List<PhonelistModel> {
    return phonelists
  }

  override fun create(phonelist: PhonelistModel) {
    phonelist.id = getId()
    phonelists.add(phonelist)
    logAll()
  }

  override fun update(phonelist: PhonelistModel) {
    var foundPhonelist: PhonelistModel? = phonelists.find { p -> p.id == phonelist.id }
    if (foundPhonelist != null) {
        foundPhonelist.title = phonelist.title
        foundPhonelist.description = phonelist.description
        foundPhonelist.date = phonelist.date
        foundPhonelist.image = phonelist.image
        foundPhonelist.lat = phonelist.lat
        foundPhonelist.lng = phonelist.lng
        foundPhonelist.zoom = phonelist.zoom
      logAll()
    }
  }

  override fun delete(phonelist: PhonelistModel) {
    phonelists.remove(phonelist)
  }

  fun logAll(){
    phonelists.forEach {info("${it}")}
  }

}
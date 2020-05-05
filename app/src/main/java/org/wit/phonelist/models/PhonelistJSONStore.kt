package org.wit.phonelist.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.phonelist.helpers.exists
import org.wit.phonelist.helpers.read
import org.wit.phonelist.helpers.write
import java.util.*

val JSON_FILE = "phonelists.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PhonelistModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class PhonelistJSONStore : PhonelistStore, AnkoLogger {

  val context: Context
  var phonelists = mutableListOf<PhonelistModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<PhonelistModel> {
    return phonelists
  }

  override fun create(phonelist: PhonelistModel) {
    phonelist.id = generateRandomId()
    phonelists.add(phonelist)
    serialize()
  }


  override fun update(phonelist: PhonelistModel) {
    val phonelistsList = findAll() as ArrayList<PhonelistModel>
    var foundPhonelist: PhonelistModel? = phonelistsList.find { p -> p.id == phonelist.id }
    if (foundPhonelist != null) {
      foundPhonelist.title = phonelist.title
      foundPhonelist.description = phonelist.description
      foundPhonelist.date = phonelist.date
      foundPhonelist.image = phonelist.image
      foundPhonelist.lat = phonelist.lat
      foundPhonelist.lng = phonelist.lng
      foundPhonelist.zoom = phonelist.zoom
    }
    serialize()
  }

  override fun delete(phonelist: PhonelistModel) {
    phonelists.remove(phonelist)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(phonelists, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    phonelists = Gson().fromJson(jsonString, listType)
  }
}

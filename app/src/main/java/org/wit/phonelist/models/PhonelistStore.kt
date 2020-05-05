package org.wit.phonelist.models

interface PhonelistStore {
  fun findAll(): List<PhonelistModel>
  fun create(phonelist: PhonelistModel)
  fun update(phonelist: PhonelistModel)
  fun delete(phonelist: PhonelistModel)
}
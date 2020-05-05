package org.wit.phonelist.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivityForResult
import org.wit.phonelist.R

class PhonelistWelcome:AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)

    toolbarW.title = title

    welcome.setOnClickListener(){
      startActivityForResult<PhonelistListActivity>(0)
    }
  }
}
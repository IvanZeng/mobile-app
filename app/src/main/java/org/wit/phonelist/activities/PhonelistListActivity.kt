package org.wit.phonelist.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_phonelist_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.phonelist.R
import org.wit.phonelist.main.MainApp
import org.wit.phonelist.models.PhonelistModel

class PhonelistListActivity : AppCompatActivity(), PhonelistListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_phonelist_list)
    app = application as MainApp

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager   //recyclerView is a widget in activity_phonelist_list.xml
    loadPhonelists()

    toolbarMain.title = title
    setSupportActionBar(toolbarMain)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<PhonelistActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPhonelistClick(phonelist: PhonelistModel) {
    startActivityForResult(intentFor<PhonelistActivity>().putExtra("phonelist_edit", phonelist), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadPhonelists()
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun loadPhonelists() {
    showPhonelists(app.phonelists.findAll())
  }

  fun showPhonelists (phonelists: List<PhonelistModel>) {
    recyclerView.adapter = PhonelistAdapter(phonelists, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }
}
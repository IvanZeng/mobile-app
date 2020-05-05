package org.wit.phonelist.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_phonelist.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.phonelist.R
import org.wit.phonelist.helpers.readImage
import org.wit.phonelist.helpers.readImageFromPath
import org.wit.phonelist.helpers.showImagePicker
import org.wit.phonelist.main.MainApp
import org.wit.phonelist.models.Location
import org.wit.phonelist.models.PhonelistModel
import java.util.*

class PhonelistActivity : AppCompatActivity(), AnkoLogger {

  var phonelist = PhonelistModel()
  lateinit var app : MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_phonelist)
    app = application as MainApp
    var edit = false

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    if (intent.hasExtra("phonelist_edit")) {
      edit = true
      phonelist = intent.extras.getParcelable<PhonelistModel>("phonelist_edit")
      phonelistTitle.setText(phonelist.title)
      description.setText(phonelist.description)
      date.setText(phonelist.date)
      phonelistImage.setImageBitmap(readImageFromPath(this, phonelist.image))
      if (phonelist.image != null){
        chooseImage.setText(R.string.change_phonelist_image)
      }
      btnAdd.setText(R.string.save_phonelist)
    }

    phonelistLocation.setOnClickListener{
      val location = Location(52.245696, -7.139102, 15f)
      if (phonelist.zoom != 0f) {
        location.lat =  phonelist.lat
        location.lng = phonelist.lng
        location.zoom = phonelist.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    btnAdd.setOnClickListener() {
      phonelist.title = phonelistTitle.text.toString()
      phonelist.description = description.text.toString()
      phonelist.date = date.text.toString()

      if (phonelist.title.isEmpty()) {
        toast(R.string.enter_phonelist_title)
      } else {
        if (edit) {
          app.phonelists.update(phonelist.copy())
        } else {
          app.phonelists.create(phonelist.copy())
        }
      }
      info("add Button Pressed: $phonelistTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)

  }

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    date.setRawInputType(InputType.TYPE_NULL)
    //button click to show DatePickerDialog
    date.setOnClickListener{
      val dpd = DatePickerDialog(this,
        DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
          //set to edit text
          date.setText(""+mYear+"-"+(mMonth+1)+"-"+mDay)
        },year,month,day)
      //show dialog
      dpd.show()
    }

  }



  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_phonelist, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        app.phonelists.delete(phonelist)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          phonelist.image = data.getData().toString()
          phonelistImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_phonelist_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          phonelist.lat = location.lat
          phonelist.lng = location.lng
          phonelist.zoom = location.zoom
        }
      }
    }
  }
}
package org.wit.phonelist.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_phonelist.view.*
import org.wit.phonelist.R
import org.wit.phonelist.helpers.readImageFromPath
import org.wit.phonelist.models.PhonelistModel

interface PhonelistListener {
  fun onPhonelistClick(phonelist: PhonelistModel)
}

class PhonelistAdapter constructor(private var phonelists: List<PhonelistModel>,
                                   private val listener: PhonelistListener) : RecyclerView.Adapter<PhonelistAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_phonelist, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val phonelist = phonelists[holder.adapterPosition]
    holder.bind(phonelist, listener)
  }

  override fun getItemCount(): Int = phonelists.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(phonelist: PhonelistModel,  listener : PhonelistListener) {
      itemView.phonelistTitle.text = phonelist.title
      itemView.description.text = phonelist.description
      itemView.dateC.text = phonelist.date
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, phonelist.image))
      itemView.setOnClickListener { listener.onPhonelistClick(phonelist) }
    }
  }
}
package com.example.testovoe_zadanie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testovoe_zadanie.databinding.ApplicationItemBinding

class ApplicationAdapter : RecyclerView.Adapter<ApplicationAdapter.AppViewHolder>() {
  private val applicationList = ArrayList<Application>()

  class AppViewHolder(val binding: ApplicationItemBinding) : RecyclerView.ViewHolder(binding.root) {

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ApplicationItemBinding.inflate(inflater, parent, false)
    return AppViewHolder(binding)
  }

  override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
    val application = applicationList[position]
    with(holder.binding) {
      titleTextView.text = application.title
      imgView.setImageDrawable(application.img)
      cardView.tag = application
    }
  }

  override fun getItemCount(): Int {
    return applicationList.size
  }

  fun addAllApplications(appList: List<Application>) {
    applicationList.clear()
    applicationList.addAll(appList)
    notifyDataSetChanged()
  }
}
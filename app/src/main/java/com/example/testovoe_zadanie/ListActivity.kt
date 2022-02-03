package com.example.testovoe_zadanie

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testovoe_zadanie.databinding.ActivityListBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityListBinding
  private val adapter = ApplicationAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListBinding.inflate(layoutInflater)
    setContentView(binding.root)
    init()
    changeTheme()
  }

  override fun onStart() {
    super.onStart()
    changeTheme()
  }

  override fun onResume() {
    super.onResume()
    changeTheme()
  }

  fun onClickItem(view: View) {
    val appInfoList = intent.getParcelableArrayListExtra<ApplicationInfo>(Constants.appList)
    val application = view.tag as Application
    val app = appInfoList?.find { it.loadLabel(packageManager).toString() == application.title }
    val intent = Intent(this, ItemActivity::class.java)

    intent.putExtra(Constants.appTitle, application.title)
    intent.putExtra(Constants.appImg, application.img.toString())
    intent.putExtra(Constants.appDate, application.date)
    intent.putExtra(Constants.appSdk, application.sdk)
    intent.putExtra(Constants.appSize, application.size)
    intent.putExtra(Constants.application, app)

    startActivity(intent)
  }

  fun onCLickChangeTheme(view: View) {
    GlobalVariables.lightTheme = !binding.chipListActivity.isChecked
    changeTheme()
  }

  private fun init() {
    val appInfoList = intent.getParcelableArrayListExtra<ApplicationInfo>(Constants.appList)
    val appList: ArrayList<Application> = ArrayList()
    binding.apply {
      rcView.layoutManager = GridLayoutManager(this@ListActivity, 2)
      rcView.adapter = adapter

      for (i in 0 until appInfoList!!.size) {
        val fullDate = Date(packageManager.getPackageInfo(appInfoList[i].packageName.toString(), 0).firstInstallTime)
        val date = SimpleDateFormat("DD.MM.yyyy").format(fullDate)
        val file = File(appInfoList[i].publicSourceDir)
        val size = Formatter.formatFileSize(applicationContext, file.length())

        val application = Application(
          appInfoList[i].loadIcon(packageManager),
          appInfoList[i].loadLabel(packageManager).toString(),
          appInfoList[i].targetSdkVersion.toString(),
          date,
          size
        )
        appList.add(application)
      }
      adapter.addAllApplications(appList)
    }
  }

  private fun changeTheme() {
    binding.apply {
      chipListActivity.isChecked = !GlobalVariables.lightTheme
      if (GlobalVariables.lightTheme) {
        chipListActivity.isChipIconVisible = true
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      } else {
        chipListActivity.isChipIconVisible = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      }
    }
  }
}

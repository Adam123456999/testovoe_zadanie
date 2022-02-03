package com.example.testovoe_zadanie

import android.Manifest
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testovoe_zadanie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private var appList = ArrayList<ApplicationInfo>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
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

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      Constants.RQ_PERMISSIONS_FOR_READ_STORAGE_CODE -> {
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
          Toast.makeText(this, "Нужно предоставить разрешение на чтение данных устройства!", Toast.LENGTH_LONG).show()
        }
      }
    }
  }

  fun onClickGoListActivity(view: View) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
        PackageManager.PERMISSION_GRANTED) {
      scan()
      val intent = Intent(this, ListActivity::class.java)
      intent.putExtra(Constants.appList, appList)
      startActivity(intent)
    } else {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        Constants.RQ_PERMISSIONS_FOR_READ_STORAGE_CODE
      )
    }
  }

  fun onCLickChangeTheme(view: View) {
    GlobalVariables.lightTheme = !binding.chipMainActivity.isChecked
    changeTheme()
  }

  private fun scan() {
    val temp = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    Toast.makeText(this, "Идёт сканирование", Toast.LENGTH_SHORT).show()
    MyClass.getApplications(temp).forEach {
      appList.add(it)
    }
  }

  private fun changeTheme() {
    binding.apply {
      chipMainActivity.isChecked = !GlobalVariables.lightTheme
      if (GlobalVariables.lightTheme) {
        chipMainActivity.isChipIconVisible = true
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      } else {
        chipMainActivity.isChipIconVisible = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      }
    }
  }
}
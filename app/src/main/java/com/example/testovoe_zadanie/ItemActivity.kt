package com.example.testovoe_zadanie

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.testovoe_zadanie.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {
  private lateinit var binding: ActivityItemBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityItemBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val title = intent.getStringExtra(Constants.appTitle)
    val sdk = intent.getStringExtra(Constants.appSdk)
    val date = intent.getStringExtra(Constants.appDate)
    val size = intent.getStringExtra(Constants.appSize)

    val app = intent.getParcelableExtra<ApplicationInfo>(Constants.application)
    val img = app?.loadIcon(packageManager)

    changeTheme()

    binding.apply {
      tvDateValue.text = date
      tvSDKValue.text = sdk
      tvTitle.text = title
      tvSizeValue.text = size
      imgItemIcon.setImageDrawable(img)
    }
  }

  override fun onStart() {
    super.onStart()
    changeTheme()
  }

  override fun onResume() {
    super.onResume()
    changeTheme()
  }

  fun onCLickChangeTheme(view: View) {
    GlobalVariables.lightTheme = !binding.chipItemActivity.isChecked
    changeTheme()
  }


  private fun changeTheme() {
    binding.apply {
      chipItemActivity.isChecked = !GlobalVariables.lightTheme
      if (GlobalVariables.lightTheme) {
        chipItemActivity.isChipIconVisible = true
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        container.setBackgroundResource(R.drawable.container_light)
      } else {
        chipItemActivity.isChipIconVisible = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        container.setBackgroundResource(R.drawable.container_dark)
      }
    }
  }

  fun onClickBack(view: View) = finish()
}

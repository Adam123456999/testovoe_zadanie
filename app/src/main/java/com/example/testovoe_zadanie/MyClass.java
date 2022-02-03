package com.example.testovoe_zadanie;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyClass {
  static List<ApplicationInfo> getApplications(List<ApplicationInfo> list) {
    List<ApplicationInfo> result = new ArrayList<>();

    for (ApplicationInfo packageInfo : list)
      if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
        result.add(packageInfo);

    return result;
  }
}
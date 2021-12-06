package com.zerdaket.example

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerdaket.example.data.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author zerdaket
 * @date 2021/12/6 10:28 下午
 */
class MainViewModel: ViewModel() {

    val appListLiveData = MutableLiveData<List<AppInfo>>()

    fun getAppList() {
        viewModelScope.launch(Dispatchers.Main) {
            val list = queryAppList()
            appListLiveData.value = list
        }
    }

    private suspend fun queryAppList() = withContext(Dispatchers.IO) {
        val packageManager: PackageManager = App.instance().packageManager
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        packageManager.queryIntentActivities(intent, 0).map {
            AppInfo(it.loadLabel(packageManager).toString(), it.loadIcon(packageManager))
        }
    }

}
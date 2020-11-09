package com.redinput.battstats

import android.app.ActivityManager
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.redinput.battstats.service.BatteryService
import com.redinput.battstats.widget.BatteryWidget

@Suppress("DEPRECATION")
fun Context.isServiceRunning(): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (BatteryService::class.java.name == service.service.className) {
            return true
        }
    }
    return false
}

fun Context.updateWidgets() {
    val widgetManager = AppWidgetManager.getInstance(this)
    val widgetIds = widgetManager.getAppWidgetIds(ComponentName(this, BatteryWidget::class.java))

    val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, BatteryWidget::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
    sendBroadcast(intent)
}

fun <T> MutableLiveData<T>.update(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}

fun View.setVisible(visible: Boolean) {
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.INVISIBLE
    }
}

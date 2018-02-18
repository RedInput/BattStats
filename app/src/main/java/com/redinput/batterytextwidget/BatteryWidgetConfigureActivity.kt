package com.redinput.batterytextwidget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.battery_widget_configure.*

class BatteryWidgetConfigureActivity : AppCompatActivity() {

    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.battery_widget_configure)
        setSupportActionBar(toolbar)

        setResult(Activity.RESULT_CANCELED)

        // Find the widget id from the intent.
        val intent = getIntent()
        val extras = intent.getExtras()
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        cancelWidget.setOnClickListener { finish() }

        saveWidget.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            var selection = PreferenceHelper.WidgetStyle.TEXT
            if (selectedId == radioNumber.id) {
                selection = PreferenceHelper.WidgetStyle.NUMBER
            } else if (selectedId == radioText.id) {
                selection = PreferenceHelper.WidgetStyle.TEXT
            }
            prefs.saveWidgetStyle(mAppWidgetId, selection)

            BatteryWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }

}


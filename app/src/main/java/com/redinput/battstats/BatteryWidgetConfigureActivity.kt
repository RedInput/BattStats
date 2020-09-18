package com.redinput.battstats

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        val powerUsageIntent = Intent(Intent.ACTION_POWER_USAGE_SUMMARY)
        val resolveInfo = packageManager.resolveActivity(powerUsageIntent, 0)
        if (resolveInfo != null) {
            behaviourUsage.visibility = View.VISIBLE
            behaviourUsage.isChecked = true
        } else {
            behaviourUsage.visibility = View.GONE
            behaviourUsage.isChecked = false
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
            prefs.saveWidgetBackground(mAppWidgetId, background.isChecked)
            prefs.saveWidgetBehaviour(mAppWidgetId, behaviourUsage.isChecked)

            BatteryWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }

}

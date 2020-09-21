package com.redinput.battstats.ui.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.redinput.battstats.R
import com.redinput.battstats.Widget
import kotlinx.android.synthetic.main.battery_widget_configure.*

class BatteryWidgetConfigureActivity : AppCompatActivity() {

    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.battery_widget_configure)
        setSupportActionBar(toolbar)

        setResult(Activity.RESULT_CANCELED)

        // Find the widget id from the intent.
        if (intent.extras != null) {
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
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
            val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

            val showAsText = radioGroup.checkedRadioButtonId == R.id.radioText
            val textColor = ContextCompat.getColor(this, R.color.white)
            val bgColor = ContextCompat.getColor(this, R.color.black)
            val widgetConfig = Widget.Config(mAppWidgetId, showAsText, textColor, background.isChecked, bgColor, Widget.ActionType.BATTERY)

            BatteryWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), mAppWidgetId, batteryIntent, widgetConfig)

            val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }

}

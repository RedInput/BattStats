package com.redinput.battstats.ui.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ibm.icu.text.RuleBasedNumberFormat
import com.redinput.battstats.R
import com.redinput.battstats.Widget.DisplayStyle.NUMBER
import com.redinput.battstats.Widget.DisplayStyle.TEXT
import com.redinput.battstats.databinding.BatteryWidgetConfigureBinding
import com.redinput.battstats.setVisible
import java.util.*

class BatteryWidgetConfigureActivity : AppCompatActivity() {

    private val viewModel: BatteryWidgetConfigureViewModel by viewModels()
    private lateinit var binding: BatteryWidgetConfigureBinding

    private val formatter = RuleBasedNumberFormat(Locale.getDefault(), RuleBasedNumberFormat.SPELLOUT)

    private val level = 57

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val widgetId = intent?.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val resultValue = Intent().apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        }
        setResult(Activity.RESULT_CANCELED, resultValue)

        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
        viewModel.setId(widgetId)

        binding = BatteryWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.widgetConfig.observe(this, {
            val showLevel = when (it.displayStyle) {
                TEXT -> formatter.format(level)
                NUMBER -> level.toString()
            }
            binding.previewWidget.appwidgetText.text = showLevel
            binding.previewWidget.appwidgetText.isAllCaps = it.textCaps

            if ((binding.previewBatteryStatus.checkedButtonId == R.id.status_low) && (!it.lowEnabled)) {
                binding.previewBatteryStatus.check(R.id.status_base)
            }
            binding.statusLow.isEnabled = it.lowEnabled

            if ((binding.previewBatteryStatus.checkedButtonId == R.id.status_charging) && (!it.chargingEnabled)) {
                binding.previewBatteryStatus.check(R.id.status_base)
            }
            binding.statusCharging.isEnabled = it.chargingEnabled

            if ((binding.previewBatteryStatus.checkedButtonId == R.id.status_full) && (!it.fullEnabled)) {
                binding.previewBatteryStatus.check(R.id.status_base)
            }
            binding.statusFull.isEnabled = it.fullEnabled

            if (binding.previewBatteryStatus.checkedButtonId == R.id.status_base) {
                binding.previewWidget.appwidgetText.setTextColor(it.baseTextColor)
                binding.previewWidget.appwidgetBackground.setVisible(it.baseBackgroundEnabled)
                binding.previewWidget.appwidgetBackground.setBackgroundColor(it.baseBackgroundColor)

            } else if (binding.previewBatteryStatus.checkedButtonId == R.id.status_low) {
                binding.previewWidget.appwidgetText.setTextColor(it.lowTextColor)
                binding.previewWidget.appwidgetBackground.setVisible(it.lowBackgroundEnabled)
                binding.previewWidget.appwidgetBackground.setBackgroundColor(it.lowBackgroundColor)

            } else if (binding.previewBatteryStatus.checkedButtonId == R.id.status_charging) {
                binding.previewWidget.appwidgetText.setTextColor(it.chargingTextColor)
                binding.previewWidget.appwidgetBackground.setVisible(it.chargingBackgroundEnabled)
                binding.previewWidget.appwidgetBackground.setBackgroundColor(it.chargingBackgroundColor)

            } else if (binding.previewBatteryStatus.checkedButtonId == R.id.status_full) {
                binding.previewWidget.appwidgetText.setTextColor(it.fullTextColor)
                binding.previewWidget.appwidgetBackground.setVisible(it.fullBackgroundEnabled)
                binding.previewWidget.appwidgetBackground.setBackgroundColor(it.fullBackgroundColor)
            }
        })

        binding.previewBatteryStatus.addOnButtonCheckedListener { _, _, _ ->
            viewModel.forceReload()
        }
    }
}

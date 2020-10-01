package com.redinput.battstats.ui.widget

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import com.jaredrummler.android.colorpicker.ColorPreferenceCompat
import com.redinput.battstats.R
import com.redinput.battstats.helpers.BasePreferenceFragment
import java.util.*

class BatteryWidgetConfigureFragment : BasePreferenceFragment() {

    private val viewModel: BatteryWidgetConfigureViewModel by activityViewModels()

    private lateinit var prefDisplayType: ListPreference
    private lateinit var prefTextCaps: SwitchPreferenceCompat
    private lateinit var prefClickAction: ListPreference

    private lateinit var prefBaseTextColor: ColorPreferenceCompat
    private lateinit var prefBaseBackgroundEnabled: SwitchPreferenceCompat
    private lateinit var prefBaseBackgroundColor: ColorPreferenceCompat

    private lateinit var prefLowEnabled: SwitchPreferenceCompat
    private lateinit var prefLowTextColor: ColorPreferenceCompat
    private lateinit var prefLowBackgroundEnabled: SwitchPreferenceCompat
    private lateinit var prefLowBackgroundColor: ColorPreferenceCompat

    private lateinit var prefChargingEnabled: SwitchPreferenceCompat
    private lateinit var prefChargingTextColor: ColorPreferenceCompat
    private lateinit var prefChargingBackgroundEnabled: SwitchPreferenceCompat
    private lateinit var prefChargingBackgroundColor: ColorPreferenceCompat

    private lateinit var prefFullEnabled: SwitchPreferenceCompat
    private lateinit var prefFullTextColor: ColorPreferenceCompat
    private lateinit var prefFullBackgroundEnabled: SwitchPreferenceCompat
    private lateinit var prefFullBackgroundColor: ColorPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.battery_widget_config)

        prefDisplayType = findPreference(getString(R.string.key_display_type))!!
        prefDisplayType.onPreferenceChangeListener = preferenceListener

        prefTextCaps = findPreference(getString(R.string.key_text_caps))!!
        prefTextCaps.onPreferenceChangeListener = preferenceListener

        prefClickAction = findPreference(getString(R.string.key_click_action))!!
        prefClickAction.onPreferenceChangeListener = preferenceListener


        prefBaseTextColor = findPreference(getString(R.string.key_base_text_color))!!
        prefBaseTextColor.onPreferenceChangeListener = preferenceListener

        prefBaseBackgroundEnabled = findPreference(getString(R.string.key_base_backgound_enabled))!!
        prefBaseBackgroundEnabled.onPreferenceChangeListener = preferenceListener

        prefBaseBackgroundColor = findPreference(getString(R.string.key_base_backgound_color))!!
        prefBaseBackgroundColor.onPreferenceChangeListener = preferenceListener


        prefLowEnabled = findPreference(getString(R.string.key_low_enabled))!!
        prefLowEnabled.onPreferenceChangeListener = preferenceListener

        prefLowTextColor = findPreference(getString(R.string.key_low_text_color))!!
        prefLowTextColor.onPreferenceChangeListener = preferenceListener

        prefLowBackgroundEnabled = findPreference(getString(R.string.key_low_backgound_enabled))!!
        prefLowBackgroundEnabled.onPreferenceChangeListener = preferenceListener

        prefLowBackgroundColor = findPreference(getString(R.string.key_low_backgound_color))!!
        prefLowBackgroundColor.onPreferenceChangeListener = preferenceListener


        prefChargingEnabled = findPreference(getString(R.string.key_charging_enabled))!!
        prefChargingEnabled.onPreferenceChangeListener = preferenceListener

        prefChargingTextColor = findPreference(getString(R.string.key_charging_text_color))!!
        prefChargingTextColor.onPreferenceChangeListener = preferenceListener

        prefChargingBackgroundEnabled = findPreference(getString(R.string.key_charging_backgound_enabled))!!
        prefChargingBackgroundEnabled.onPreferenceChangeListener = preferenceListener

        prefChargingBackgroundColor = findPreference(getString(R.string.key_charging_backgound_color))!!
        prefChargingBackgroundColor.onPreferenceChangeListener = preferenceListener


        prefFullEnabled = findPreference(getString(R.string.key_full_enabled))!!
        prefFullEnabled.onPreferenceChangeListener = preferenceListener

        prefFullTextColor = findPreference(getString(R.string.key_full_text_color))!!
        prefFullTextColor.onPreferenceChangeListener = preferenceListener

        prefFullBackgroundEnabled = findPreference(getString(R.string.key_full_backgound_enabled))!!
        prefFullBackgroundEnabled.onPreferenceChangeListener = preferenceListener

        prefFullBackgroundColor = findPreference(getString(R.string.key_full_backgound_color))!!
        prefFullBackgroundColor.onPreferenceChangeListener = preferenceListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.widgetConfig.observe(viewLifecycleOwner, Observer {
            val displayStyle = it.displayStyle.name.toLowerCase(Locale.ROOT)
            if (displayStyle != prefDisplayType.value) {
                prefDisplayType.value = displayStyle
                prefTextCaps.isEnabled = (displayStyle == "text")
            }
            if (it.textCaps != prefTextCaps.isChecked) {
                prefTextCaps.isChecked = it.textCaps
            }
            val clickAction = it.clickAction.name.toLowerCase(Locale.ROOT)
            if (clickAction != prefClickAction.value) {
                prefClickAction.value = clickAction
            }

            if (it.baseTextColor != prefBaseTextColor.color) {
                prefBaseTextColor.color = it.baseTextColor
            }
            if (it.baseBackgroundEnabled != prefBaseBackgroundEnabled.isChecked) {
                prefBaseBackgroundEnabled.isChecked = it.baseBackgroundEnabled
            }
            if (it.baseBackgroundColor != prefBaseBackgroundColor.color) {
                prefBaseBackgroundColor.color = it.baseBackgroundColor
            }

            if (it.lowEnabled != prefLowEnabled.isChecked) {
                prefLowEnabled.isChecked = it.lowEnabled
            }
            if (it.lowTextColor != prefLowTextColor.color) {
                prefLowTextColor.color = it.lowTextColor
            }
            if (it.lowBackgroundEnabled != prefLowBackgroundEnabled.isChecked) {
                prefLowBackgroundEnabled.isChecked = it.lowBackgroundEnabled
            }
            if (it.lowBackgroundColor != prefLowBackgroundColor.color) {
                prefLowBackgroundColor.color = it.lowBackgroundColor
            }

            if (it.chargingEnabled != prefChargingEnabled.isChecked) {
                prefChargingEnabled.isChecked = it.chargingEnabled
            }
            if (it.chargingTextColor != prefChargingTextColor.color) {
                prefChargingTextColor.color = it.chargingTextColor
            }
            if (it.chargingBackgroundEnabled != prefChargingBackgroundEnabled.isChecked) {
                prefChargingBackgroundEnabled.isChecked = it.chargingBackgroundEnabled
            }
            if (it.chargingBackgroundColor != prefChargingBackgroundColor.color) {
                prefChargingBackgroundColor.color = it.chargingBackgroundColor
            }

            if (it.fullEnabled != prefFullEnabled.isChecked) {
                prefFullEnabled.isChecked = it.fullEnabled
            }
            if (it.fullTextColor != prefFullTextColor.color) {
                prefFullTextColor.color = it.fullTextColor
            }
            if (it.fullBackgroundEnabled != prefFullBackgroundEnabled.isChecked) {
                prefFullBackgroundEnabled.isChecked = it.fullBackgroundEnabled
            }
            if (it.fullBackgroundColor != prefFullBackgroundColor.color) {
                prefFullBackgroundColor.color = it.fullBackgroundColor
            }
        })
    }

    private val preferenceListener = Preference.OnPreferenceChangeListener { preference: Preference, newValue: Any ->
        when (preference) {
            prefDisplayType -> {
                prefTextCaps.isEnabled = (newValue == "text")
                viewModel.changeDisplayStyle(newValue as String)
            }
            prefTextCaps -> viewModel.changeTextCaps(newValue as Boolean)
            prefClickAction -> viewModel.changeClickAction(newValue as String)

            prefBaseTextColor -> viewModel.changeBaseTextColor(newValue as Int)
            prefBaseBackgroundEnabled -> viewModel.changeBaseBackgroundEnabled(newValue as Boolean)
            prefBaseBackgroundColor -> viewModel.changeBaseBackgroundColor(newValue as Int)

            prefLowEnabled -> viewModel.changeLowEnabled(newValue as Boolean)
            prefLowTextColor -> viewModel.changeLowTextColor(newValue as Int)
            prefLowBackgroundEnabled -> viewModel.changeLowBackgroundEnabled(newValue as Boolean)
            prefLowBackgroundColor -> viewModel.changeLowBackgroundColor(newValue as Int)

            prefChargingEnabled -> viewModel.changeChargingEnabled(newValue as Boolean)
            prefChargingTextColor -> viewModel.changeChargingTextColor(newValue as Int)
            prefChargingBackgroundEnabled -> viewModel.changeChargingBackgroundEnabled(newValue as Boolean)
            prefChargingBackgroundColor -> viewModel.changeChargingBackgroundColor(newValue as Int)

            prefFullEnabled -> viewModel.changeFullEnabled(newValue as Boolean)
            prefFullTextColor -> viewModel.changeFullTextColor(newValue as Int)
            prefFullBackgroundEnabled -> viewModel.changeFullBackgroundEnabled(newValue as Boolean)
            prefFullBackgroundColor -> viewModel.changeFullBackgroundColor(newValue as Int)
        }
        true
    }
}
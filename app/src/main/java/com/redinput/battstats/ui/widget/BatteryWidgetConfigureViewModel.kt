package com.redinput.battstats.ui.widget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redinput.battstats.Widget
import com.redinput.battstats.Widget.ActionType.*
import com.redinput.battstats.Widget.DisplayStyle.NUMBER
import com.redinput.battstats.Widget.DisplayStyle.TEXT
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.domain.SaveWidgetConfig
import com.redinput.battstats.forceRefresh
import com.redinput.battstats.update
import com.redinput.battstats.updateWidgets

class BatteryWidgetConfigureViewModel(application: Application) : AndroidViewModel(application) {

    private val prefRepository = PreferencesRepository.getInstance(application.applicationContext)
    private val saveWidgetConfig = SaveWidgetConfig(prefRepository)

    private val _widgetConfig = MutableLiveData(Widget.Config(application.applicationContext))
    val widgetConfig: LiveData<Widget.Config> = _widgetConfig

    fun forceReload() {
        _widgetConfig.forceRefresh()
    }

    fun setId(id: Int) {
        _widgetConfig.update { it.value?.id = id }
    }


    fun changeDisplayStyle(value: String) {
        if (value == "text") {
            _widgetConfig.update { it.value?.displayStyle = TEXT }
        } else if (value == "number") {
            _widgetConfig.update { it.value?.displayStyle = NUMBER }
        }
    }

    fun changeTextCaps(enabled: Boolean) {
        _widgetConfig.update { it.value?.textCaps = enabled }
    }

    fun changeClickAction(value: String) {
        if (value == "battery") {
            _widgetConfig.update { it.value?.clickAction = BATTERY }
        } else if (value == "config") {
            _widgetConfig.update { it.value?.clickAction = CONFIG }
        } else if (value == "none") {
            _widgetConfig.update { it.value?.clickAction = NONE }
        }
    }


    fun changeBaseTextColor(color: Int) {
        _widgetConfig.update { it.value?.baseTextColor = color }
    }

    fun changeBaseBackgroundEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.baseBackgroundEnabled = enabled }
    }

    fun changeBaseBackgroundColor(color: Int) {
        _widgetConfig.update { it.value?.baseBackgroundColor = color }
    }


    fun changeLowEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.lowEnabled = enabled }
    }

    fun changeLowTextColor(color: Int) {
        _widgetConfig.update { it.value?.lowTextColor = color }
    }

    fun changeLowBackgroundEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.lowBackgroundEnabled = enabled }
    }

    fun changeLowBackgroundColor(color: Int) {
        _widgetConfig.update { it.value?.lowBackgroundColor = color }
    }


    fun changeChargingEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.chargingEnabled = enabled }
    }

    fun changeChargingTextColor(color: Int) {
        _widgetConfig.update { it.value?.chargingTextColor = color }
    }

    fun changeChargingBackgroundEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.chargingBackgroundEnabled = enabled }
    }

    fun changeChargingBackgroundColor(color: Int) {
        _widgetConfig.update { it.value?.chargingBackgroundColor = color }
    }


    fun changeFullEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.fullEnabled = enabled }
    }

    fun changeFullTextColor(color: Int) {
        _widgetConfig.update { it.value?.fullTextColor = color }
    }

    fun changeFullBackgroundEnabled(enabled: Boolean) {
        _widgetConfig.update { it.value?.fullBackgroundEnabled = enabled }
    }

    fun changeFullBackgroundColor(color: Int) {
        _widgetConfig.update { it.value?.fullBackgroundColor = color }
    }

    fun saveWidgetConfig() {
        saveWidgetConfig.invoke(_widgetConfig.value!!){
            getApplication<Application>().applicationContext.updateWidgets()
        }
    }
}
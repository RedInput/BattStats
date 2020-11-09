package com.redinput.battstats.widget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redinput.battstats.Widget
import com.redinput.battstats.Widget.ActionType
import com.redinput.battstats.Widget.DisplayStyle
import com.redinput.battstats.data.PreferencesRepository
import com.redinput.battstats.domain.LoadWidgetConfig
import com.redinput.battstats.domain.SaveWidgetConfig
import com.redinput.battstats.forceRefresh
import com.redinput.battstats.helpers.UseCase
import com.redinput.battstats.update
import com.redinput.battstats.updateWidgets
import java.util.*

class BatteryWidgetConfigureViewModel(application: Application) : AndroidViewModel(application) {

    private val prefRepository = PreferencesRepository.getInstance(application.applicationContext)
    private val saveWidgetConfig = SaveWidgetConfig(prefRepository)
    private val loadWidgetConfig = LoadWidgetConfig(prefRepository)

    private val _widgetConfig = MutableLiveData(Widget.Config(application.applicationContext))
    val widgetConfig: LiveData<Widget.Config> = _widgetConfig

    fun forceReload() {
        _widgetConfig.forceRefresh()
    }

    fun loadWidget(widgetId: Int) {
        loadWidgetConfig.invoke(widgetId) {
            if ((it is UseCase.Result.Success<*>)
                && (it.data is Widget.Config)) {
                _widgetConfig.value = it.data
            } else {
                _widgetConfig.update { it.value?.id = widgetId }
            }
        }
    }


    fun changeDisplayStyle(value: String) {
        val newValue = DisplayStyle.valueOf(value.toUpperCase(Locale.ROOT))
        _widgetConfig.update { it.value?.displayStyle = newValue }
    }

    fun changeTextCaps(enabled: Boolean) {
        _widgetConfig.update { it.value?.textCaps = enabled }
    }

    fun changeClickAction(value: String) {
        val newValue = ActionType.valueOf(value.toUpperCase(Locale.ROOT))
        _widgetConfig.update { it.value?.clickAction = newValue }
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
        saveWidgetConfig.invoke(_widgetConfig.value!!) {
            getApplication<Application>().applicationContext.updateWidgets()
        }
    }
}
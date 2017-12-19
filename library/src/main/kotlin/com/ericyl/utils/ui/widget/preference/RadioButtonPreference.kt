package com.ericyl.utils.ui.widget.preference

import android.content.Context
import android.preference.CheckBoxPreference
import com.ericyl.utils.R


class RadioButtonPreference(var value: String, context: Context) : CheckBoxPreference(context) {
    init {
        widgetLayoutResource = R.layout.layout_preference_radio_button
    }

}
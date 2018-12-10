package com.ericyl.utils.ui.widget.preference

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import com.ericyl.utils.R

/**
 * @author Danesh
 * @author nebkat
 * @author ericyl 【Update】
 */
private const val MIN_VALUE = 1
private const val DEFAULT_VALUE = 1
private const val MAX_VALUE = 1

class NumberPickerPreference(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : DialogPreference(context, attrs, defStyleAttr) {

    private val min: Int
    private val max: Int
    private var defaultValue: Int = this.getPersistedInt(DEFAULT_VALUE)
    private val textEnable: Boolean
    private val textDigits: String
    private val maxExternalKey: String
    private val minExternalKey: String
    private lateinit var numberPicker: NumberPicker

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.NumberPickerPreference, defStyleAttr, R.style.defaultStyle_NumberPickerPreference)
        try {
            //todo
            maxExternalKey = a.getString(R.styleable.NumberPickerPreference_maxExternal)!!
            minExternalKey = a.getString(R.styleable.NumberPickerPreference_minExternal)!!
            max = a.getInt(R.styleable.NumberPickerPreference_max, MAX_VALUE)
            min = a.getInt(R.styleable.NumberPickerPreference_min, MIN_VALUE)
            textEnable = a.getBoolean(R.styleable.NumberPickerPreference_textEnable, false)
            textDigits = a.getString(R.styleable.NumberPickerPreference_textDigits)!!
        } finally {
            a.recycle()
        }
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        super.onSetInitialValue(restorePersistedValue, defaultValue)
        if (!restorePersistedValue) {
            this.defaultValue = defaultValue as Int
            persistInt(this.defaultValue)
        }
    }

    override fun onCreateView(parent: ViewGroup?): View {
        super.onCreateView(parent)
//      External values
        val max = sharedPreferences.getInt(maxExternalKey, this.max)
        val min = sharedPreferences.getInt(minExternalKey, this.min)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_number_picker_dialog, null)
        numberPicker = view.findViewById(R.id.number_picker)
        numberPicker
//      Initialize state
        numberPicker.maxValue = max
        numberPicker.minValue = min
        numberPicker.value = getPersistedInt(defaultValue)
        numberPicker.wrapSelectorWheel = false

//      No keyboard popup
//      com.android.internal.R.id.numberpicker_input
        val textInput = numberPicker.findViewById<EditText>(Resources.getSystem().getIdentifier("numberpicker_input", "id", "android"))
        textInput.isEnabled = true
        textInput.keyListener = DigitsKeyListener.getInstance(textDigits)
        textInput.isCursorVisible = false
        textInput.isFocusable = textEnable
        textInput.isFocusableInTouchMode = textEnable
        return view
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        return a.getInteger(index, DEFAULT_VALUE)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            numberPicker.clearFocus()
            persistInt(numberPicker.value)
        }
    }


}
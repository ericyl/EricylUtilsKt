package com.ericyl.utils.ui.widget.preference

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.preference.Preference
import android.preference.PreferenceCategory
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.AttributeSet
import com.ericyl.utils.R


class RadioGroupPreference(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : PreferenceCategory(context, attrs, defStyleAttr), Preference.OnPreferenceClickListener {

    private lateinit var radioValue: String
    private var radioEntries: Array<CharSequence>
    private var radioEntryValues: Array<CharSequence>
    private var radioEntrySummaries: Array<CharSequence>?

    private lateinit var onRadioButtonClickListener: OnRadioButtonClickListener

    fun setOnRadioButtonClickListener(onRadioButtonClickListener: OnRadioButtonClickListener) {
        this.onRadioButtonClickListener = onRadioButtonClickListener
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RadioGroupPreference, defStyleAttr, 0)
        try {
            radioEntries = a.getTextArray(R.styleable.RadioGroupPreference_radioEntries)
            radioEntryValues = a.getTextArray(R.styleable.RadioGroupPreference_radioEntryValues)
            radioEntrySummaries = a.getTextArray(R.styleable.RadioGroupPreference_radioEntrySummaries)
        } finally {
            a.recycle()
        }
    }

    fun getRadioValue(): String {
        return radioValue
    }

    private fun setRadioValue(radioValue: String) {
        this.radioValue = radioValue
        persistString(radioValue)
    }


    fun setPreferenceEnable(value: String, enabled: Boolean) {
        getRadioButtonPreference(value)?.isEnabled = enabled
    }


    fun setPreferenceSummary(value: String, summary: String) {
        val radioButtonPreference = getRadioButtonPreference(value)
        radioButtonPreference?.summary = summary
    }

    private fun getRadioButtonPreference(value: String): RadioButtonPreference? {
        return (0 until preferenceCount)
                .mapNotNull { getPreference(it) as? RadioButtonPreference }
                .firstOrNull { it.value == value }
    }


    private fun setOtherPreferenceUnChecked(paramRadioButtonPreference: RadioButtonPreference) {
        (0 until preferenceCount)
                .map { getPreference(it) as RadioButtonPreference }
                .filter { it !== paramRadioButtonPreference }
                .forEach { it.isChecked = false }
    }

    override fun onAttachedToHierarchy(preferenceManager: PreferenceManager) {
        super.onAttachedToHierarchy(preferenceManager)
        for (entry in radioEntries.withIndex()) {
            if (!TextUtils.isEmpty(entry.value)) {
                val value = radioEntryValues[entry.index]
                val radioButtonPreference = RadioButtonPreference(value.toString(), context)
                radioButtonPreference.title = entry.value
                radioButtonPreference.isPersistent = false
                radioButtonPreference.isChecked = radioValue == value
                radioButtonPreference.summary = radioEntrySummaries?.get(entry.index)
                addPreference(radioButtonPreference)
            }
        }
    }


    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference !is RadioButtonPreference)
            return false
        return if (onRadioButtonClickListener.onPreferenceClick(this, preference)) {
            setOtherPreferenceUnChecked(preference)
            preference.isChecked = true
            setRadioValue(preference.value)
            true
        } else false
    }

    override fun onPrepareAddPreference(preference: Preference): Boolean {
        if (preference is RadioButtonPreference) {
            return if (super.onPrepareAddPreference(preference)) {
                preference.setOnPreferenceClickListener(this)
                true
            } else false
        }
        return false
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)
        radioValue = state.value
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        if (isPersistent) {
            return superState
        }
        val myState = SavedState(superState)
        myState.value = radioValue
        return myState
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any) {
        super.onSetInitialValue(restorePersistedValue, defaultValue)
        if (restorePersistedValue) {
            radioValue = getPersistedString(null)
        } else {
            radioValue = defaultValue as String
            persistString(radioValue)
        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        return a.getString(index)
    }

    internal class SavedState : Preference.BaseSavedState {

        lateinit var value: String

        constructor(source: Parcel) : super(source) {
            value = source.readString()
        }

        constructor(parcelable: Parcelable) : super(parcelable)

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(value)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

}

interface OnRadioButtonClickListener {
    fun onPreferenceClick(radioGroupPreference: RadioGroupPreference, radioButtonPreference: RadioButtonPreference): Boolean
}


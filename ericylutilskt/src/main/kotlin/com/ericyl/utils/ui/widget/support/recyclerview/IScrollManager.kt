package com.ericyl.utils.ui.widget.support.recyclerview

import android.support.v7.widget.RecyclerView

interface IScrollManager {
    fun isTop(recyclerView: RecyclerView): Boolean

    fun isBottom(recyclerView: RecyclerView): Boolean
}
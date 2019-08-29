package com.ericyl.utils.ui.widget.support.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface IScrollManager {
    fun isTop(recyclerView: RecyclerView): Boolean

    fun isBottom(recyclerView: RecyclerView): Boolean
}
package com.ericyl.utils.ui.widget.support.recyclerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseLinearLayoutManager(context: Context) : LinearLayoutManager(context), IScrollManager {

    /**
     * @param context     Current context, will be used to access resources.
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    constructor(context: Context, orientation: Int) : this(context) {
        this.orientation = orientation
    }

    override fun isTop(recyclerView: RecyclerView): Boolean {
        return 0 == findFirstVisibleItemPosition()
    }

    override fun isBottom(recyclerView: RecyclerView): Boolean {
        val lastVisiblePosition = findLastCompletelyVisibleItemPosition()
        val lastPosition = recyclerView.adapter!!.itemCount - 1
        return lastVisiblePosition == lastPosition
    }

    //    @Override
    //    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
    //        try {
    //            super.onLayoutChildren(recycler, state);
    //        } catch (IndexOutOfBoundsException e) {
    //            e.printStackTrace();
    //        }
    //    }
}
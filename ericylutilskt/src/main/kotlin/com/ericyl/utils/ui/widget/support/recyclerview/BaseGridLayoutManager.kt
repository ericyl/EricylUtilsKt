package com.ericyl.utils.ui.widget.support.recyclerview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class BaseGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount), IScrollManager {

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
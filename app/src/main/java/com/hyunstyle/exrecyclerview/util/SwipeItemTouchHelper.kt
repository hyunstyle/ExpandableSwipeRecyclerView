package com.hyunstyle.exrecyclerview.util

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hyunstyle on 2019.05.04
 */
class SwipeItemTouchHelper(dragDirs: Int, swipeDirs: Int,
                           private val swipeItemTouchListener: SwipeItemTouchListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeItemTouchListener.onSwiped(viewHolder, direction)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        swipeItemTouchListener.onSelectedChanged(viewHolder, actionState, ItemTouchHelper.Callback.getDefaultUIUtil())
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        swipeItemTouchListener.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, ItemTouchHelper.Callback.getDefaultUIUtil())
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        swipeItemTouchListener.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive, ItemTouchHelper.Callback.getDefaultUIUtil())
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        swipeItemTouchListener.clearView(recyclerView, viewHolder, ItemTouchHelper.Callback.getDefaultUIUtil())
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = ItemTouchHelper.START
        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFlags)
    }

}
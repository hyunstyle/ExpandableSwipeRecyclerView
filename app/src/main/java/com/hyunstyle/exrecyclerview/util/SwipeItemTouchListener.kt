package com.hyunstyle.exrecyclerview.util

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hyunstyle on 2019.05.04
 */
interface SwipeItemTouchListener {
    fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int)
    fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int, uiUtil: ItemTouchUIUtil)
    fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    )

    fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView, holder: RecyclerView.ViewHolder?, dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    )

    fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil)
}
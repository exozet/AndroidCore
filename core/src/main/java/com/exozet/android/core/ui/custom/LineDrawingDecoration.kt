package com.exozet.android.core.ui.custom

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.fievx.polet.DrawingDecoration

/**
 * Draws a line a either top, bottom, left or right position.
 * You can add multiple LineDrawingDecoration to the drawingDecoration list of you Polet to have multiple lines drawn if
 * needed.
 */
class LineDrawingDecoration(@ColorInt color: Int, val position: Position, val thickness: Int, val marginTop: Int = 0, val marginBottom: Int = 0, val marginLeft: Int = 0, val marginRight: Int = 0) :
    DrawingDecoration {
    private val paint = Paint().apply {
        this.color = color
    }

    override fun onDraw(rect: Rect, view: View, c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when (position) {
            Position.top ->
                c.drawRect(
                    (view.left - rect.left + marginLeft).toFloat(),
                    (view.top - rect.top).toFloat(),
                    (view.right + rect.right - marginRight).toFloat(),
                    (view.top - rect.top + thickness).toFloat(),
                    paint
                )
            Position.bottom ->
                c.drawRect(
                    (view.left - rect.left + marginLeft).toFloat(),
                    (view.bottom + rect.bottom - thickness).toFloat(),
                    (view.right + rect.right - marginRight).toFloat(),
                    (view.bottom + rect.bottom).toFloat(),
                    paint
                )
            Position.left ->
                c.drawRect(
                    (view.left - rect.left).toFloat(),
                    (view.top - rect.top).toFloat(),
                    (view.left - rect.left + thickness).toFloat(),
                    (view.bottom + rect.bottom).toFloat(),
                    paint
                )
            Position.right ->
                c.drawRect(
                    (view.right + rect.right - thickness).toFloat(),
                    (view.top - rect.top).toFloat(),
                    (view.right + rect.right).toFloat(),
                    (view.bottom + rect.bottom).toFloat(),
                    paint
                )
        }
    }

    enum class Position {
        top,
        bottom,
        left,
        right
    }

}
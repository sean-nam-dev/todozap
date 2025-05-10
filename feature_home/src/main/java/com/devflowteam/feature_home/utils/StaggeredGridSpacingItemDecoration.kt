package com.devflowteam.feature_home.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingDp: Float,
    private val includeEdge: Boolean,
    private val context: Context
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
        val spacingPx = dpToPx(spacingDp)

        if (includeEdge) {
            outRect.left = spacingPx - spanIndex * spacingPx / spanCount
            outRect.right = (spanIndex + 1) * spacingPx / spanCount

            if (position < spanCount) {
                outRect.top = spacingPx
            }
            outRect.bottom = spacingPx
        } else {
            outRect.left = spanIndex * spacingPx / spanCount
            outRect.right = spacingPx - (spanIndex + 1) * spacingPx / spanCount

            if (position >= spanCount) {
                outRect.top = spacingPx
            }
        }
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}
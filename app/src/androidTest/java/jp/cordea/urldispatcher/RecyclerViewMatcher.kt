package jp.cordea.urldispatcher

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

fun withText(
        position: Int,
        @IdRes res: Int,
        text: String
) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description?) {
    }

    override fun matchesSafely(item: RecyclerView?): Boolean {
        val view = item ?: return false
        val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
        return viewHolder.itemView.findViewById<TextView>(res).text == text
    }
}

fun isSize(
        size: Int
) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
    override fun describeTo(description: Description?) {
    }

    override fun matchesSafely(item: RecyclerView?): Boolean {
        val view = item ?: return false
        return view.adapter?.itemCount == size
    }
}

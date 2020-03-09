package com.memo.pay.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageButton

class CheckableView : AppCompatImageButton, Checkable {

    private var mChecked = false

    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    /**
     * Interface definition for a callback to be invoked when the checked state of this View is changed.
     */
    interface OnCheckedChangeListener {

        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param checkableView The view whose state has changed.
         * @param isChecked     The new checked state of checkableView.
         */
        fun onCheckedChanged(checkableView: View, isChecked: Boolean)

    }


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setChecked(checked: Boolean) {
        if (checked != mChecked) {
            mChecked = checked
            refreshDrawableState()

            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener!!.onCheckedChanged(this, mChecked)
            }
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }


    override fun onCreateDrawableState(extraSpace: Int): IntArray {

        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    /**
     * Register a callback to be invoked when the checked state this view changes.
     *
     * @param listener the callback to call on checked state checked
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        this.mOnCheckedChangeListener = listener
    }

    companion object {

        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}
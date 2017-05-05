package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import eu.inloop.simplerecycleradapter.SettableViewHolder
import kotlinx.android.synthetic.main.card_month_view.view.*

class MonthSmokeFreeCardViewHolder : SettableViewHolder<SmokeFreeMonth> {

    constructor(itemView: View) : super(itemView) {
    }

    constructor(context: Context, @LayoutRes layoutRes: Int, parent: ViewGroup)
            : super(context, layoutRes, parent) {
    }

    override fun setData(smokeFreeMonth: SmokeFreeMonth) {
        itemView.monthView.setSmokeFreeMonth(smokeFreeMonth)
    }
}

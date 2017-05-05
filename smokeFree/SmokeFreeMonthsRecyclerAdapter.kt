package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import android.content.Context
import android.view.ViewGroup
import com.balauris.smotivator.R
import eu.inloop.simplerecycleradapter.SettableViewHolder
import eu.inloop.simplerecycleradapter.SimpleRecyclerAdapter

class SmokeFreeMonthsRecyclerAdapter(val context: Context)
    : SimpleRecyclerAdapter.CreateViewHolder<SmokeFreeMonth>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : SettableViewHolder<SmokeFreeMonth> {
        return MonthSmokeFreeCardViewHolder(context,
                R.layout.card_month_view, parent)
    }
}
package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import java.util.*

data class SmokeFreeMonth(val quittingStart: Calendar?,
                          val quittingEnd: Calendar?,
                          val isFirstMonth: Boolean,
                          val isLastMonth: Boolean)
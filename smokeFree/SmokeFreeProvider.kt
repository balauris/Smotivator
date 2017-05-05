package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import com.balauris.smotivator.utils.data.UserData
import rx.Observable

interface SmokeFreeProvider {
    fun provideUserData(): Observable<UserData>

    fun getCurrentSystemTime(): Long
}
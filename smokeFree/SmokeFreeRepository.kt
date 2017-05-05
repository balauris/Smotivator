package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import com.balauris.smotivator.utils.data.UserData
import rx.Observable

interface SmokeFreeRepository {
    fun getUserData(): Observable<UserData>
}
package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import com.balauris.smotivator.utils.data.UserData
import rx.Observable

class SmokeFreeProviderImpl(val repository: SmokeFreeRepositoryImpl) : SmokeFreeProvider {

    override fun getCurrentSystemTime(): Long {
        return System.currentTimeMillis()
    }

    override fun provideUserData(): Observable<UserData> {
        return repository.getUserData()
    }
}
package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import com.balauris.smotivator.utils.data.UserData
import io.realm.Realm
import rx.Observable
import rx.lang.kotlin.toSingletonObservable

class SmokeFreeRepositoryImpl : SmokeFreeRepository {

    override fun getUserData(): Observable<UserData> {
        val realm = Realm.getDefaultInstance()
        return realm.where(UserData::class.java).findFirst().toSingletonObservable()
    }
}
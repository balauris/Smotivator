package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import com.balauris.smotivator.utils.presenter.BasePresenterImpl
import rx.Subscription
import rx.lang.kotlin.subscribeWith

class SmokeFreePresenterImpl(val provider: SmokeFreeProviderImpl)
    : BasePresenterImpl<SmokeFreeView>(), SmokeFreePresenter {

    lateinit var subscription: Subscription

    override fun onViewCreated() {
        provider.provideUserData().subscribeWith {
            onNext {
                onView {
                    showMonthSmokeFreeList(
                            SmokeFreeMonthsGenerator().generateSmokeFreeMonthList(
                                    it.quittingDate,
                                    getUserLocale()))
                }
            }
            onError {
                logFirebaseCrash("SmokeFree provider could not retrieve data from repository!")
            }
        }
        //start observable here if constant updating is needed
    }

    override fun onCreate() {

    }
}
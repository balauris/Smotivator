package com.balauris.smotivator.utils.presenter

import android.app.Activity
import android.os.Build
import android.os.Bundle
import com.balauris.smotivator.BaseApplication
import com.google.firebase.crash.FirebaseCrash
import java.util.*

open class BasePresenterImpl<V> : BasePresenter<V> {
    private var view: V? = null

    private var activity: Activity? = null

    private var firebaseAnalytics = BaseApplication.getFirebaseAnalytics()

    override fun setupBasePresenter(view: V?,
                                    activity: Activity?) {
        this.view = view
        this.activity = activity
    }

    override fun dropView() {
        view = null
    }

    override fun setCurrentScreenInAnalytics(screen: String) {
        checkIfActivityIsNotNull(screen)
        firebaseAnalytics.setCurrentScreen(activity!!, "General_statistics_screen", null)
    }

    override fun logFirebaseCrash(event: String) {
        FirebaseCrash.log(event)
    }

    override fun logFirebaseAnalyticsEvent(event: String, bundle: Bundle?) {
        firebaseAnalytics.logEvent(event, bundle)
    }

    override fun logFirebaseAnalyticsEvent(event: String) {
        logFirebaseAnalyticsEvent(event, null)
    }

    override fun createBundleFromHashMap(map: HashMap<*, *>): Bundle {
        val bundle = Bundle()
        for ((key, value) in map) {
            bundle.putString(key.toString(), value.toString())
        }
        return bundle
    }

    override fun getUserLocale(): Locale {
        return Locale.getDefault()
    }
    // Local functions place!

    private fun checkIfActivityIsNotNull(screen: String) {
        if (activity == null) {
            logFirebaseCrash("Screen: $screen Activity is null, can't log events")
        }
    }

    fun hasView() = view != null

    fun onView(action: V.() -> Unit) {
        if (hasView()) {
            action.invoke(view!!)
        }
    }
}

package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import java.util.*

class SmokeFreeMonthsGenerator {

    lateinit var userLocale: Locale

    fun generateSmokeFreeMonthList(quittingDateInMillis: Long, userLocale: Locale):
            ArrayList<SmokeFreeMonth> {
        this.userLocale = userLocale
        val userQuittingCalendar = getUserQuittingCalendar(quittingDateInMillis)
        val currentSystemCalendar = getCurrentCalendar()
        val differentMonthCountWhenQuitting = calculateDifferentMonthCountWhenQuitting(
                userQuittingCalendar, currentSystemCalendar)

        return createMonthList(differentMonthCountWhenQuitting,
                userQuittingCalendar, currentSystemCalendar)
    }

    private fun createMonthList(differentMonthCountWhenQuitting: Int,
                                userQuittingCalendar: Calendar,
                                currentSystemCalendar: Calendar): ArrayList<SmokeFreeMonth> {

        val smokeFreeMonths: ArrayList<SmokeFreeMonth> = ArrayList()
        var monthAfterQuittingIndex = 0

        if (onlyOneMonthWhenQuitting(differentMonthCountWhenQuitting)) {
            addOnlyOneMonthToList(currentSystemCalendar, smokeFreeMonths, userQuittingCalendar)
            return smokeFreeMonths
        }
        while (monthAfterQuittingIndex <= differentMonthCountWhenQuitting) {
            if (isFirstMonth(monthAfterQuittingIndex)) {
                addFirstMonthOfQuitting(smokeFreeMonths, userQuittingCalendar)
            } else if (isLastMonth(monthAfterQuittingIndex, differentMonthCountWhenQuitting)) {
                addLastMonthOfQuitting(currentSystemCalendar, smokeFreeMonths)
            } else {
                addFullMonthToList(monthAfterQuittingIndex, smokeFreeMonths, userQuittingCalendar)
            }
            monthAfterQuittingIndex++
        }
        return smokeFreeMonths
    }

    private fun addFullMonthToList(monthAfterQuittingIndex: Int,
                                   smokeFreeMonths: ArrayList<SmokeFreeMonth>,
                                   userQuittingCalendar: Calendar) {
        smokeFreeMonths.add(SmokeFreeMonth(generateMonthCalendar(userQuittingCalendar,
                monthAfterQuittingIndex), null, false, false))
    }

    private fun addLastMonthOfQuitting(currentSystemCalendar: Calendar,
                                       smokeFreeMonths: ArrayList<SmokeFreeMonth>) {
        smokeFreeMonths.add(SmokeFreeMonth(null, currentSystemCalendar, false, true))
    }

    private fun addFirstMonthOfQuitting(smokeFreeMonths: ArrayList<SmokeFreeMonth>,
                                        userQuittingCalendar: Calendar) {
        smokeFreeMonths.add(SmokeFreeMonth(userQuittingCalendar, null, true, false))
    }

    private fun addOnlyOneMonthToList(currentSystemCalendar: Calendar,
                                      smokeFreeMonths: ArrayList<SmokeFreeMonth>,
                                      userQuittingCalendar: Calendar) {
        smokeFreeMonths.add(SmokeFreeMonth(userQuittingCalendar, currentSystemCalendar, true, true))
    }

    private fun onlyOneMonthWhenQuitting(differentMonthCountWhenQuitting: Int)
            = differentMonthCountWhenQuitting == 1

    private fun generateMonthCalendar(userQuittingCalendar: Calendar,
                                      monthAfterQuittingIndex: Int): Calendar {
        val tempCal = Calendar.getInstance(userLocale)
        tempCal.timeInMillis = userQuittingCalendar.timeInMillis
        tempCal.add(Calendar.MONTH, monthAfterQuittingIndex)
        return tempCal
    }

    private fun isFirstMonth(monthAfterQuittingIndex: Int) = monthAfterQuittingIndex == 0

    private fun isLastMonth(monthAfterQuitting: Int, totalMonthsCount: Int): Boolean
            = monthAfterQuitting == totalMonthsCount

    //Different month count when quitting i.e. started - Jan 31, current - Feb 1 result = 2
    private fun calculateDifferentMonthCountWhenQuitting(quittingTime: Calendar,
                                                         currentTime: Calendar): Int {
        val yearsDifference = currentTime.get(Calendar.YEAR) - quittingTime.get(Calendar.YEAR)
        val monthDifference = yearsDifference * MONTHS_IN_YEAR +
                currentTime.get(Calendar.MONTH) - quittingTime.get(Calendar.MONTH)
        return monthDifference
    }

    private fun getCurrentCalendar(): Calendar {
        return Calendar.getInstance(userLocale)
    }

    private fun getUserQuittingCalendar(userQuittingTimeInMillis: Long): Calendar {
        val userQuittingCalendar = Calendar.getInstance(userLocale)
        userQuittingCalendar.timeInMillis = userQuittingTimeInMillis
        return userQuittingCalendar
    }

    companion object {
        val MONTHS_IN_YEAR = 12
    }
}
package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import com.balauris.smotivator.R

// danger zone! active war is going on here, proceed with caution, its far from being finished.
open class MonthView : View {

    var currentYCoordinate = 0f

    private var monthNeedsExtraWeek: Boolean = false

    val defaultLocale: Locale = Locale.getDefault()
    val paddingFromSides: Int =
            resources.getDimension(R.dimen.calendar_padding_from_sides).toInt()
    val paddingTopAndBottom: Int =
            resources.getDimension(R.dimen.calendar_padding_from_top_and_bottom).toInt()
    val doneIcon: Bitmap = BitmapFactory.decodeResource(context.resources,
            R.drawable.day_tick)

    private var userQuittingMonth: SmokeFreeMonth? = null
    private val currentCalendar: Calendar = Calendar.getInstance(defaultLocale)

    lateinit var calendarToDraw: Calendar
    lateinit var calendarDayPaint: Paint
    lateinit var calendarMonthHeaderPaint: Paint
    lateinit var doneDayBitmapPaint: Paint

    init {
        initPaint()
    }

    private fun initPaint() {
        calendarDayPaint = Paint()
        applyDefaultPaintProperties(calendarDayPaint)
        calendarDayPaint.typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)

        calendarMonthHeaderPaint = Paint()
        applyDefaultPaintProperties(calendarMonthHeaderPaint)
        calendarMonthHeaderPaint.typeface = Typeface.create("sans-serif", Typeface.NORMAL)

        doneDayBitmapPaint = Paint()
        doneDayBitmapPaint.isAntiAlias = true
    }

    private fun applyDefaultPaintProperties(paint: Paint) {
        paint.textSize = resources.getDimension(R.dimen.smoke_free_calender_day_text_size)
        paint.color = resources.getColor(R.color.light_gray)
        paint.isAntiAlias = true
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        val oneDayDimensions = calculateCalendarDaysDimensions(width, paddingFromSides)
        if (monthIsValidFormat()) {
            return
        } else {
            if (userQuittingMonth!!.isFirstMonth && userQuittingMonth!!.isLastMonth) {
                //draw month from start to current
                drawStartingMonthWhichIsAlsoCurrent(canvas,
                        userQuittingMonth!!.quittingStart!!,
                        userQuittingMonth!!.quittingEnd!!,
                        oneDayDimensions)
            } else if (userQuittingMonth!!.isFirstMonth) {
                //draw from done start to current
            } else if (userQuittingMonth!!.isLastMonth) {
                //draw done from start to current
            } else {
                drawWholeMonthDone(canvas, userQuittingMonth!!.quittingStart!!, oneDayDimensions)
            }
        }
    }

    private fun monthIsValidFormat() =
            userQuittingMonth?.quittingEnd == null && userQuittingMonth?.quittingStart == null

    private fun calculateCalendarDaysDimensions(screenWidth: Int, paddingFromSides: Int): Int {
        return (screenWidth - paddingFromSides) / DAYS_IN_WEEK
    }

    private fun drawStartingMonthWhichIsAlsoCurrent(canvas: Canvas, startCalendar: Calendar,
                                                    endCalendar: Calendar,
                                                    oneDayDimensions: Int) {
        var dayToDraw = 1
        val tempCal = Calendar.getInstance(defaultLocale)
        tempCal.timeInMillis = startCalendar.timeInMillis
        while (dayToDraw <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            tempCal.set(Calendar.DAY_OF_MONTH, dayToDraw)
            if (startCalendar.get(Calendar.DAY_OF_MONTH) > dayToDraw &&
                    dayToDraw < endCalendar.get(Calendar.DAY_OF_MONTH)) {
                drawDoneDay(tempCal, canvas, doneIcon, oneDayDimensions, getCalendarOneSidePadding())
            } else {
                drawCalendarDayText(tempCal, canvas, oneDayDimensions, getCalendarOneSidePadding())
            }
            dayToDraw++
        }
    }

    private fun drawWholeMonthDone(canvas: Canvas, monthCalendar: Calendar, oneDayDimensions: Int) {
        var dayToDraw = 1
        while (dayToDraw <= monthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            monthCalendar.set(Calendar.DAY_OF_MONTH, dayToDraw)
            drawDoneDay(monthCalendar, canvas, doneIcon, oneDayDimensions, getCalendarOneSidePadding())
            dayToDraw++
        }
    }

    private fun getCalendarOneSidePadding() = paddingFromSides / 2

    private fun drawDoneDay(calendar: Calendar, canvas: Canvas, doneIcon: Bitmap,
                            oneCellSpan: Int, paddingOneSide: Int) {

        val dayCoordinates = calculateDayCoordinates(calendar, oneCellSpan)
        val correctCoordinates = calculateLeftAndTop(dayCoordinates, doneIcon, oneCellSpan,
                paddingOneSide)

        canvas.drawBitmap(
                doneIcon,
                correctCoordinates.x.toFloat(),
                correctCoordinates.y.toFloat(),
                doneDayBitmapPaint)
    }

    private fun drawCalendarDayText(calendar: Calendar, canvas: Canvas, oneCellSpan: Int,
                                    paddingOneSide: Int) {

        val dayCoordinates = calculateDayCoordinates(calendar, oneCellSpan)
        val textRectangle = calculateTextRectangle(
                calendar.get(Calendar.DAY_OF_MONTH).toString(),
                calendarDayPaint)

        canvas.drawText(
                calendar.get(Calendar.DAY_OF_MONTH).toString(),
                dayCoordinates.x - textRectangle.right / 2f + paddingOneSide,
                dayCoordinates.y - textRectangle.bottom / 2f + paddingOneSide,
                doneDayBitmapPaint)
    }

    private fun calculateLeftAndTop(coordinates: Point, icon: Bitmap, oneCellSpan: Int,
                                    paddingOneSide: Int): Point {
        val correctCoordinates = Point()
        correctCoordinates.x = coordinates.x - oneCellSpan / 2 - icon.width / 2 + paddingOneSide
        correctCoordinates.y = coordinates.y - oneCellSpan / 2 - icon.height / 2 + paddingOneSide
        return correctCoordinates
    }


    /**
     * Calculates Right(X) and Bottom(Y) coordinates
     */
    private fun calculateDayCoordinates(calendar: Calendar, oneCellSpan: Int): Point {
        val p = Point()
        var weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        var week = calendar.get(Calendar.WEEK_OF_MONTH)
        if (weeksStartsOnMonday(calendar)) {
            weekDay = getCorrectWeekDayWhenWeekStartsOnMonday(calendar)
            if (monthNeedsExtraWeek || firstWeekOfYearIsZero(week)) {
                monthNeedsExtraWeek = true
                week += 1
            }
        } else if (weekStartsOnSunday(calendar)) {
            week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
        }
        p.x = weekDay * oneCellSpan
        p.y = week * oneCellSpan + oneCellSpan
        return p
    }

    //2017-01-01 was Sunday and WEEK_OF_MONTH == 52(strange? yeah. )
    private fun firstWeekOfYearIsZero(week: Int) = week == 0

    private fun weekStartsOnSunday(calendar: Calendar)
            = calendar.firstDayOfWeek == Calendar.SUNDAY

    private fun weeksStartsOnMonday(calendar: Calendar)
            = calendar.firstDayOfWeek == Calendar.MONDAY

    private fun getCorrectWeekDayWhenWeekStartsOnMonday(calendar: Calendar): Int {
        var currentDay = calendar.get(Calendar.DAY_OF_WEEK)
        if (itsSunday(currentDay)) {
            return SUNDAY
        }
        //correction from SUNDAY - SATURDAY week cycle and DAMMIT, use normal week pls!! because
        //it took me 15 years to figure out how that stuff works, fu...
        currentDay -= 1
        return currentDay
    }

    private fun itsSunday(currentDay: Int) = currentDay == Calendar.SUNDAY

    private fun drawMonthCalendarHeader(canvas: Canvas, monthCalendar: Calendar) {
        val headerText = monthCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, defaultLocale)
                .toUpperCase()
        val textRect = calculateTextRectangle(headerText, calendarMonthHeaderPaint)
        currentYCoordinate = textRect.height().toFloat() + paddingTopAndBottom
        canvas.drawText(headerText,
                width / 2f - textRect.width() / 2f,
                currentYCoordinate,
                calendarMonthHeaderPaint
        )
    }

    private fun calculateTextRectangle(text: String, textPaint: Paint): Rect {
        val textRectangle = Rect()
        textPaint.getTextBounds(text, 0, text.length, textRectangle)
        return textRectangle
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWeeksInThisMonth = getWeekCountFromSmokeFreeMonth()
        if (maxWeeksInThisMonth >= 0) {
            val monthViewRowsCount = maxWeeksInThisMonth + 1 //for calendar header
            val height = resources.getDimension(R.dimen.calender_one_day_height) *
                    monthViewRowsCount + paddingTopAndBottom
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height.toInt() * 2)
        } else {
            setMeasuredDimension(0, 0)
        }
    }

    private fun getWeekCountFromSmokeFreeMonth(): Int {
        return userQuittingMonth?.quittingStart?.getActualMaximum(Calendar.WEEK_OF_MONTH) ?:
                userQuittingMonth?.quittingEnd?.getActualMaximum(Calendar.WEEK_OF_MONTH) ?:
                MONTH_IS_NULL
    }

    open fun setSmokeFreeMonth(smokeFreeMonth: SmokeFreeMonth) {
        this.userQuittingMonth = smokeFreeMonth
        redrawCalendar()
    }

    private fun redrawCalendar() {
        setWillNotDraw(false)
        requestLayout()
        invalidate()
    }

    companion object {
        val DAYS_IN_WEEK = 7
        val MONTH_IS_NULL = -1
        //every value below is based on MONDAY -> SUNDAY week cycle, like it should be !
        val SUNDAY = 7
    }
}
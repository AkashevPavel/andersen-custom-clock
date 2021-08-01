package com.akashevpavel.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.util.Calendar
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.N)
class CustomClockView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val customPaint: Paint = Paint()

    private var mSecondHandColor: Int = 0
    private var mMinuteHandColor: Int = 0
    private var mHourHandColor: Int = 0
    private var mSecondHandTruncation: Float = 0f
    private var mMinuteHandTruncation: Float = 0f
    private var mHourHandTruncation: Float = 0f

    private var sec: Int = 0
    private var min = 0
    private var hour = 0

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomClockView,
            defStyle, 0).apply {
                try {
                    setHourHandColor(getColor(R.styleable.CustomClockView_hourHandColor, Color.BLACK))
                    setHourHandTruncation(getFloat(R.styleable.CustomClockView_hourHandTruncation, 0.8f))

                    setMinuteHandColor(getColor(R.styleable.CustomClockView_minuteHandColor, Color.BLUE))
                    setMinuteHandTruncation(getFloat(R.styleable.CustomClockView_minuteHandTruncation, 0.6f))

                    setSecondHandColor(getColor(R.styleable.CustomClockView_secondHandColor, Color.RED))
                    setSecondHandTruncation(getFloat(R.styleable.CustomClockView_secondHandTruncation, 0.3f))

                } finally {
                    recycle()
                }
        }

    }

    // get/set truncation
    fun getSecondHandTruncation(): Float {
        return mSecondHandTruncation

    }
    fun setSecondHandTruncation(secondHandTruncation: Float) {
        mSecondHandTruncation = secondHandTruncation
        invalidate()
        requestLayout()

    }
    fun getMinuteHandTruncation(): Float {
        return mMinuteHandTruncation

    }
    fun setMinuteHandTruncation(minuteHandTruncation: Float) {
        mMinuteHandTruncation = minuteHandTruncation
        invalidate()
        requestLayout()

    }
    fun getHourHandTruncation(): Float {
        return mHourHandTruncation

    }
    fun setHourHandTruncation(hourHandTruncation: Float) {
        mHourHandTruncation = hourHandTruncation
        invalidate()
        requestLayout()

    }

    // get/set color
    fun getSecondHandColor(): Int {
        return mSecondHandColor
    }
    fun setSecondHandColor(secondHandColor: Int) {
        mSecondHandColor = secondHandColor
        invalidate()
        requestLayout()
    }
    fun getMinuteHandColor(): Int {
        return mMinuteHandColor
    }
    fun setMinuteHandColor(minuteHandColor: Int) {
        mMinuteHandColor = minuteHandColor
        invalidate()
        requestLayout()
    }
    fun getHourHandColor(): Int {
        return mHourHandColor
    }
    fun setHourHandColor(hourHandColor: Int) {
        mHourHandColor = hourHandColor
        invalidate()
        requestLayout()
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun getTime() {
        val calendar = Calendar.getInstance()
        sec = calendar.get(Calendar.SECOND)
        min = calendar.get(Calendar.MINUTE)
        hour = calendar.get(Calendar.HOUR)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        getTime()

        val radius = (min(height, width) / 2f) - 32
        val centerX = width / 2f
        val centerY = height / 2f

        customPaint.apply {
            reset()
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 12f
            isAntiAlias = true
        }

        canvas.drawCircle(centerX, centerY, radius, customPaint)

        for (i in 1..12) {
            canvas.drawLine(centerX - radius, centerY, centerX - radius + 20f, centerY, customPaint)
            canvas.rotate(30f, centerX, centerY)
        }

        //drawing hour hand
        canvas.save()
        canvas.rotate(360 / 12 * hour + min * 0.5f, centerX, centerY)
        customPaint.color = getHourHandColor()
        canvas.drawLine(centerX, centerY, centerX, centerY - radius * getHourHandTruncation(), customPaint)
        canvas.restore()

        //drawing minute hand
        canvas.save()
        canvas.rotate(360 / 60 * min + sec * 0.1f, centerX, centerY)
        customPaint.color = getMinuteHandColor()
        canvas.drawLine(centerX, centerY, centerX, centerY - radius * getMinuteHandTruncation(), customPaint)
        canvas.restore()

        //drawing second hand
        canvas.save()
        canvas.rotate(360f / 60 * sec, centerX, centerY)
        customPaint.color = getSecondHandColor()
        canvas.drawLine(centerX, centerY, centerX, centerY - radius * getSecondHandTruncation(), customPaint)
        canvas.restore()


        postInvalidateDelayed(500)


    }




}
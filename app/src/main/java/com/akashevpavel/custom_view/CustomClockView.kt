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


    private var sec: Int = 0
    private var min = 0
    private var hour = 0


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
        canvas.drawLine(centerX, centerY, centerX, centerY - height / 5, customPaint)
        canvas.restore()

        //drawing minute hand
        canvas.save()
        canvas.rotate(360 / 60 * min + sec * 0.1f, centerX, centerY)
        customPaint.color = Color.BLUE
        canvas.drawLine(centerX, centerY, centerX, centerY - height / 7, customPaint)
        canvas.restore()

        //drawing second hand
        canvas.save()
        canvas.rotate(360f / 60 * sec, centerX, centerY)
        customPaint.color = Color.RED
        canvas.drawLine(centerX, centerY, centerX, centerY - height / 9, customPaint)
        canvas.restore()


        postInvalidateDelayed(500)


    }


}
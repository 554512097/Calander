package com.hotniao.project.calander.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hotniao.project.calander.model.Day;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：Calander
 * 类描述：
 * 创建人：kevinxie
 * 创建时间：2017/8/9 17:42
 * 修改人：
 * 修改时间：2017/8/9 17:42
 * 修改备注：
 * Version:  1.0.0
 */
public class DayView extends View {
    private Day mDay;
    private Paint mDatePaint;

    public void setDay(Day day) {
        mDay = day;
    }

    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDatePaint.setColor(Color.RED);
        mDatePaint.setTextSize(50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int length = width > height ? height : width;
        setMeasuredDimension(length, length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(String.valueOf(mDay.date), getWidth() / 2, getHeight() / 2, mDatePaint);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mDatePaint);
    }
}

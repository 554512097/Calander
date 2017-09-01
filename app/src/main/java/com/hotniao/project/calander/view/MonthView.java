package com.hotniao.project.calander.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hotniao.project.calander.OnDaySelectListener;
import com.hotniao.project.calander.R;
import com.hotniao.project.calander.model.Day;
import com.hotniao.project.calander.model.Month;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：Calander
 * 类描述：
 * 创建人：kevinxie
 * 创建时间：2017/8/9 18:12
 * 修改人：
 * 修改时间：2017/8/9 18:12
 * 修改备注：
 * Version:  1.0.0
 */
public class MonthView extends View {
    private Paint mDayDisablePaint;
    private Paint mSelectPaint;
    private Paint mDividerPaint;
    private Paint mMonthTitlePaint;
    private Paint mGeneratePaint;
    private Paint mDayPaint;
    private Month mMonth;
    private int mTitleHeight = 100;
    private Paint mDayBgPaint;
    private OnDaySelectListener mDaySelectListener;
    private Day mStartDay;
    private Day mEndDay;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayPaint.setColor(Color.DKGRAY);
        mDayPaint.setTextSize(25);

        mDayDisablePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayDisablePaint.setColor(Color.LTGRAY);
        mDayDisablePaint.setTextSize(25);

        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint.setColor(Color.parseColor("#32d3e2"));

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(Color.parseColor("#f2f3f4"));

        mDayBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDayBgPaint.setColor(Color.GREEN);
        mDayBgPaint.setStyle(Paint.Style.STROKE);

        mMonthTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMonthTitlePaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        mMonthTitlePaint.setTextSize(35);

        mGeneratePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGeneratePaint.setColor(Color.parseColor("#d6f6f9"));
    }

    public void setMonth(Month month) {
        mMonth = month;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int length = width / 7;
        ArrayList<Day> days = mMonth.mDays;
        for (int i = 0; i < days.size(); i++) {
            Day day = days.get(i);
            if (i == 0) {
                //                day.mRect = new Rect(0, 0 + mTitleHeight, 0 + length, 0 + mTitleHeight + length);
                day.mRect = new Rect((mMonth.firstDayOfWeek - 1) * length, 0 + mTitleHeight, mMonth.firstDayOfWeek * length, 0 + mTitleHeight + length);
            } else {
                Day lastDay = days.get(i - 1);
                if ((i + mMonth.firstDayOfWeek) % 7 == 1) {
                    //                if (day.dayOfWeek == 1) {
                    day.mRect = new Rect(0, lastDay.mRect.bottom, 0 + length, lastDay.mRect.bottom + length);
                } else {
                    day.mRect = new Rect(lastDay.mRect.right, lastDay.mRect.top, lastDay.mRect.right + length, lastDay.mRect.bottom);
                }
            }
            Log.d("vivi", "date:" + day.date + "----dayOfWeek:" + day.dayOfWeek + "---left:" + day.mRect.left + "----top:" + day.mRect.top + "----right:" + day.mRect.right + "----bottom" + day.mRect.bottom);
        }

        int height = days.get(days.size() - 1).mRect.bottom - days.get(0).mRect.top + mTitleHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画    月标题
        StringBuilder monthTitleSb = new StringBuilder();
        monthTitleSb.append(mMonth.year).append("年").append(mMonth.month).append("月");
        String monthTitle = monthTitleSb.toString();
        Rect rect = new Rect();
        mMonthTitlePaint.getTextBounds(monthTitle, 0, monthTitle.length(), rect);
        int titleTextWidth = rect.width();
        int titleTextHeight = rect.height();
        canvas.drawText(monthTitle, (getWidth() - titleTextWidth) / 2, (mTitleHeight + titleTextHeight) / 2, mMonthTitlePaint);

        //分割线
        canvas.drawLine(0, mTitleHeight, getWidth(), mTitleHeight, mDividerPaint);

        ArrayList<Day> days = mMonth.mDays;
        for (int i = 0; i < days.size(); i++) {
            Day day = days.get(i);
            if (mStartDay != null && compareTowDay(mStartDay, day) == 0) {
                canvas.drawRect(day.mRect, mSelectPaint);
            } else if (mEndDay != null && compareTowDay(mEndDay, day) == 0) {
                canvas.drawRect(day.mRect, mSelectPaint);
            } else if (mStartDay != null && mEndDay != null && compareTowDay(mStartDay, day) < 0 && compareTowDay(mEndDay, day) > 0) {
                canvas.drawRect(day.mRect, mGeneratePaint);
            } else {
                //                canvas.drawCircle(day.mRect.left + radius, day.mRect.top + radius, radius, mDayBgPaint);
            }


            mDayPaint.getTextBounds(String.valueOf(day.date), 0, String.valueOf(day.date).length(), rect);
            int dayTextWidth = rect.width();
            int dayTextHeight = rect.height();
            canvas.drawText(String.valueOf(day.date),
                    (day.mRect.width() - dayTextWidth) / 2 + day.mRect.left,
                    (day.mRect.height() + dayTextHeight) / 2 + day.mRect.top,
                    day.isDisable ? mDayDisablePaint : mDayPaint);

            //画     日期分割线
            int itemHeight = getWidth() / 7;
            int dividerHeight = itemHeight + mTitleHeight;
            while (dividerHeight <= getWidth()) {
                canvas.drawLine(0, dividerHeight, getWidth(), dividerHeight, mDividerPaint);
                dividerHeight += itemHeight;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            Log.d("vivi", "x:" + x + "-----y:" + y);
            if (mDaySelectListener != null) {
                mDaySelectListener.onDaySelect(getChooseDay(x, y));
            }
        }
        return true;
    }

    /**
     * @param one
     * @param two
     * @return <0    one < two     >0     one > two      = 0   one = two
     */
    private int compareTowDay(Day one, Day two) {
        Calendar instance = Calendar.getInstance();
        instance.set(one.year, one.month - 1, one.date);
        Date oneDate = instance.getTime();
        instance.set(two.year, two.month - 1, two.date);
        Date twoDate = instance.getTime();
        return oneDate.compareTo(twoDate);
    }

    private Day getChooseDay(float x, float y) {
        for (int i = 0; i < mMonth.mDays.size(); i++) {
            Day day = mMonth.mDays.get(i);
            if (!day.isDisable) {
                if (x > day.mRect.left && x < day.mRect.right && y > day.mRect.top && y < day.mRect.bottom) {
                    return day;
                }
            }
        }
        return null;
    }

    public void setOnDaySelectListener(OnDaySelectListener listener) {
        mDaySelectListener = listener;
    }

    public void updateSelect(Day startDay, Day endDay) {
        mStartDay = startDay;
        mEndDay = endDay;
    }
}

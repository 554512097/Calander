package com.hotniao.project.calander;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hotniao.project.calander.model.Day;
import com.hotniao.project.calander.model.Month;
import com.hotniao.project.calander.view.MonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：Calander
 * 类描述：
 * 创建人：kevinxie
 * 创建时间：2017/8/14 17:18
 * 修改人：
 * 修改时间：2017/8/14 17:18
 * 修改备注：
 * Version:  1.0.0
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    private Day mStartDay;
    private Day mEndDay;
    private ArrayList<MonthView> mViews = new ArrayList<>();
    private Date mToday;
    private OnDatePickerCompleteListener mOnDatePickerCompleteListener;

    public CalendarAdapter() {
        Calendar instance = Calendar.getInstance();
        mToday = instance.getTime();
    }

    OnDaySelectListener mDaySelectListener = new OnDaySelectListener() {
        @Override
        public void onDaySelect(Day day) {
            if (day == null) {
                return;
            }
            if (mStartDay == null) {
                mStartDay = day;
            } else {
                Calendar cal = Calendar.getInstance();
                cal.set(day.year, day.month - 1, day.date);
                Date dayDate = cal.getTime();
                cal.set(mStartDay.year, mStartDay.month - 1, mStartDay.date);
                Date startDate = cal.getTime();
                if (dayDate.compareTo(startDate) > 0) {
                    mEndDay = day;
                } else {
                    mStartDay = day;
                }
            }
            updateAll(mStartDay, mEndDay);
            if (mStartDay != null && mEndDay != null && mOnDatePickerCompleteListener != null) {
                StringBuilder startSb = new StringBuilder();
                startSb.append(mStartDay.year)
                        .append("-")
                        .append(mStartDay.month)
                        .append("-")
                        .append(mStartDay.date);
                StringBuilder endSb = new StringBuilder();
                endSb.append(mEndDay.year)
                        .append("-")
                        .append(mEndDay.month)
                        .append("-")
                        .append(mEndDay.date);
                mOnDatePickerCompleteListener.onComplete(startSb.toString(), endSb.toString());
            }
        }
    };

    @Override
    public CalendarAdapter.CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthView monthView = new MonthView(parent.getContext());
        monthView.updateSelect(mStartDay, mEndDay);
        monthView.setOnDaySelectListener(mDaySelectListener);
        mViews.add(monthView);
        return new CalendarAdapter.CalendarHolder(monthView);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.CalendarHolder holder, int position) {
        ((MonthView) holder.itemView).updateSelect(mStartDay, mEndDay);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, position);
        instance.set(Calendar.DATE, 1);
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH) + 1;
        int firstDayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
        Log.d("vivi", "year + month + dayOfWeek:---------" + year + "年" + month + "月" + firstDayOfWeek);
        ArrayList<Day> days = new ArrayList<>();
        for (int i = 0; i < calDays(year, month); i++) {
            if (i > 0)
                instance.add(Calendar.DATE, 1);

            days.add(new Day(year, month,
                    instance.get(Calendar.DATE),
                    instance.get(Calendar.DAY_OF_WEEK),
                    mToday.compareTo(instance.getTime()) > 0));
        }
        ((MonthView) holder.itemView).setMonth(new Month(year, month, firstDayOfWeek, days));
    }

    private void updateAll(Day startDay, Day endDay) {
        /*RecyclerView.RecycledViewPool recycledViewPool = mRv.getRecycledViewPool();
        for (int i = 0; i < mRv.getChildCount(); i++) {
            MonthView monthView = (MonthView) mRv.getChildAt(i);
            monthView.updateSelect(startDay, endDay);
            monthView.invalidate();
        }
        for (int i = 0; i < recycledViewPool.getRecycledViewCount(0); i++) {
            ((MonthView) recycledViewPool.getRecycledView(0).itemView).updateSelect(startDay, endDay);
        }*/
        for (int i = 0; i < mViews.size(); i++) {
            MonthView monthView = mViews.get(i);
            monthView.updateSelect(startDay, endDay);
            monthView.invalidate();
        }
    }

    public void setOnDatePickerCompleteListener(OnDatePickerCompleteListener onDatePickerCompleteListener) {
        mOnDatePickerCompleteListener = onDatePickerCompleteListener;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 计算每月多少天
     */
    protected int calDays(int year, int month) {
        int day = 30;
        boolean leayyear = false;
        leayyear = year % 4 == 0 && year % 100 != 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                if (leayyear) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
        }
        return day;
    }

    public class CalendarHolder extends RecyclerView.ViewHolder {
        public CalendarHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnDatePickerCompleteListener {
        void onComplete(String start, String end);
    }
}

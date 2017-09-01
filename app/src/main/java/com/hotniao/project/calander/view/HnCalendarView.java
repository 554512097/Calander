package com.hotniao.project.calander.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hotniao.project.calander.CalendarAdapter;
import com.hotniao.project.calander.R;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：Calander
 * 类描述：
 * 创建人：kevinxie
 * 创建时间：2017/8/14 17:01
 * 修改人：
 * 修改时间：2017/8/14 17:01
 * 修改备注：
 * Version:  1.0.0
 */
public class HnCalendarView extends LinearLayout {

    private View mHeaderWeek;
    private RecyclerView mRvCalendar;
    private CalendarAdapter mCalendarAdapter;

    public HnCalendarView(Context context) {
        this(context, null);
    }

    public HnCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HnCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mHeaderWeek = View.inflate(context, R.layout.header_week, this);
        mRvCalendar = new RecyclerView(context);
        mRvCalendar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRvCalendar.setLayoutManager(new LinearLayoutManager(context));
        mCalendarAdapter = new CalendarAdapter();
        mRvCalendar.setAdapter(mCalendarAdapter);
        addView(mRvCalendar);
    }

    public void setOnDatePickerCompleteListener(CalendarAdapter.OnDatePickerCompleteListener onDatePickerCompleteListener) {
        mCalendarAdapter.setOnDatePickerCompleteListener(onDatePickerCompleteListener);
    }
}

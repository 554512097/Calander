package com.hotniao.project.calander.model;

import android.graphics.Rect;

/**
 * Copyright (C) 2017,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：Calander
 * 类描述：
 * 创建人：kevinxie
 * 创建时间：2017/8/9 17:40
 * 修改人：
 * 修改时间：2017/8/9 17:40
 * 修改备注：
 * Version:  1.0.0
 */
public class Day {
    public int year;

    public int month;
    /**
     * 几号
     */
    public int date;

    /**
     * 星期几      1日 2一 3二 4三 5四 6五 7六
     */
    public int dayOfWeek;

    /**
     * 日期下面显示的描述
     */
    public String underText;

    /**
     * 显示的矩形
     */
    public Rect mRect;

    public boolean isDisable;

    public Day(int year, int month, int date, int dayOfWeek,  boolean isDisable) {
        this(year, month, date, dayOfWeek, isDisable, "");
    }

    public Day(int year, int month, int date, int dayOfWeek, boolean isDisable, String underText) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.isDisable = isDisable;
        this.underText = underText;
    }
}

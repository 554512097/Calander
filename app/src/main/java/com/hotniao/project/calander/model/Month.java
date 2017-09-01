package com.hotniao.project.calander.model;

import java.util.ArrayList;

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
public class Month {
    public int year;

    public int month;

    /**
     * 星期几      1日 2一 3二 4三 5四 6五 7六
     */
    public int firstDayOfWeek;

    public ArrayList<Day> mDays;

    public Month(int year, int month, int firstDayOfWeek, ArrayList<Day> days) {
        this.year = year;
        this.month = month;
        this.firstDayOfWeek = firstDayOfWeek;
        mDays = days;
    }
}

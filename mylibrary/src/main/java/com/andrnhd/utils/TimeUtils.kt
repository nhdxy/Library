package com.andrnhd.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间转换类
 * Created by nhd on 2017/4/15.
 */

//将Long的date转换成String的字符串
fun formatDate(date: Long, format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()):
        String = SimpleDateFormat(format, locale).format(Date(getRealTime(date)))

//将String的字符串转换成Long的date
fun parseDate(date: String, format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()):
        Date = SimpleDateFormat(format, locale).parse(date)

//比较两个时间的大小 d1 < d2 ? -1 : 1
fun compareDate(d1: String, d2: String, format: String = "yyyy-MM-dd"):
        Int = parseDate(d1, format).compareTo(parseDate(d2, format))

fun getRealTime(time: Long):
        Long = if (time.toString().length == 10) time * 1000 else time
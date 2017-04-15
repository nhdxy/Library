package com.andrnhd.utils

/**
 * Created by nhd on 2017/4/15.
 */
/**
 * 判断手机号是否正确

 * @param tel 手机号
 * *
 * @return true为正确
 */
fun telIsCorrect(tel: String):
        Boolean = tel.matches("^1\\d{10}$".toRegex())

/**
 * 判断身份证号码是否正确

 * @param idCard
 * *
 * @return
 */
fun idCardIsCorrect(idCard: String): Boolean {
    val regEx = "(^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$)"
    return idCard.matches(regEx.toRegex())
}

/**
 * 将手机号码中间隐藏

 * @param tel 需要隐藏的手机号码
 * *
 * @return
 */
fun changeTelStyle(tel: String): String {
    val sub1 = tel.substring(0, 3)
    val sub2 = tel.substring(7)
    return sub1 + "****" + sub2
}
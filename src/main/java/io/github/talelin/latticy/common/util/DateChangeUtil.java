package io.github.talelin.latticy.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 日期时间转换工具类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/8 - 17:51
 * @since JDK1.8
 */
@Slf4j
public class DateChangeUtil {

    /**
     * 将 Date 类型的日期时间转换为字符串形式的日期时间
     *
     * @param time Date 类型的日期时间
     * @return 返回转换之后的字符串形式的日期时间
     */
    public static String dateToString(Date time) {
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将时间转化为类似 2020-08-08 17:53:30 格式的字符串返回
        return sdf.format(time);
    }

    /**
     * 将字符串形式的日期时间转换为 Date 类型的日期时间
     *
     * @param time 字符串形式的日期时间
     * @return 返回转换之后的 Date 类型的日期时间
     */
    public static Date stringToDate(String time) {
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转换为 Date 类型的日期返回
        Date date;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            log.error("将字符串形式的日期时间转换为 Date 类型的日期时间时出现异常: ", e);
            return null;
        }
        return date;
    }

}
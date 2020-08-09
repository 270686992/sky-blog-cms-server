package io.github.talelin.latticy.common.util;

import io.github.talelin.autoconfigure.exception.FailedException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;

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
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO 后续将此处记录日志,并定义内部异常处理
            throw new FailedException(CodeMessageConstant.SERVER_ERROR);
        }
        return date;
    }

}
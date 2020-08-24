package io.github.talelin.latticy.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 公用工具类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/8 - 18:03
 * @since JDK1.8
 */
public class CommonUtil {

    /**
     * 判断 expiredTime 是否已经过期
     * 判断原理: 将当前时间 now 和 expiredTime 对比
     *
     * @param expiredTime 过期时间
     * @return 过期返回 true,没有过期返回 false
     */
    public static boolean isOutOfDate(Date expiredTime) {
        if (expiredTime == null) {
            return true;
        }

        long now = Calendar.getInstance().getTimeInMillis();
        long expiredTimeStamp = expiredTime.getTime();
        return now > expiredTimeStamp;
    }

    /**
     * 计算七牛云上传 token 有效期截止时间
     *
     * @param now              当前时间
     * @param upTokenTimeLimit 七牛云上传 token 有效期,单位:秒
     * @return 返回七牛云上传 token 有效期截止时间
     */
    public static Calendar calculateQiNiuUpTokenExpiryDate(Calendar now, Integer upTokenTimeLimit) {
        // 计算七牛云上传 token 有效期结束时间
        now.add(Calendar.SECOND, upTokenTimeLimit);
        return now;
    }

}
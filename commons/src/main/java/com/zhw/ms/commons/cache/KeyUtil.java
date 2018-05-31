package com.zhw.ms.commons.cache;

import com.zhw.ms.commons.consts.JccConst;

/**
 * 缓存键共通
 * Created by ZHW on 2015/6/10.
 */
public class KeyUtil {

    public static String getKey(Object... args) {
        StringBuilder sb = new StringBuilder();
        int end = args.length - 1;
        for (int i = 0; i <= end; ++i) {
            if (i < end) {
                sb.append(args[i]).append(JccConst.SEP_COLON);
            } else {
                sb.append(args[i]);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getKey("CouponInfo", "CouponInfoList", "lock"));
    }
}

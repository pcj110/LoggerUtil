package com.huawei.zhanglong.zllog.log.logtype;


import android.util.Log;

import com.huawei.zhanglong.zllog.log.GCLogger;


/**
 * @define log基础类
 */
public class BaseLog {
    /**
     *@serialField MAX_LENGTH
     * 默认为4000行，不允许外部修改
     * **/
    private static final int MAX_LENGTH = 4000;

    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, tag, sub);
                index += MAX_LENGTH;
            }
            printSub(type, tag, msg.substring(index, length));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case GCLogger.VERBOSE:
                Log.v(tag, sub);
                break;
            case GCLogger.DEBUG:
                Log.d(tag, sub);
                break;
            case GCLogger.INFO:
                Log.i(tag, sub);
                break;
            case GCLogger.WARN:
                Log.w(tag, sub);
                break;
            case GCLogger.ERROR:
                Log.e(tag, sub);
                break;
            case GCLogger.ASSERT:
                Log.wtf(tag, sub);
                break;
        }
    }

}

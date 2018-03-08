package com.huawei.zhanglong.zllog.log;

import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.huawei.zhanglong.zllog.log.logtype.BaseLog;
import com.huawei.zhanglong.zllog.log.logtype.FileLog;
import com.huawei.zhanglong.zllog.log.logtype.JsonLog;
import com.huawei.zhanglong.zllog.log.logtype.XmlLog;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * GCLogger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 * @author jambestwick
 */
public final class GCLogger {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "KLog";
    private static final String SUFFIX = ".java";
	
    public static final int JSON_INDENT = 4;
    

    public static final int VERBOSE = 0x1;
    public static final int DEBUG = 0x2;
    public static final int INFO = 0x3;
    public static final int WARN = 0x4;
    public static final int ERROR = 0x5;
    public static final int ASSERT = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final int STACK_TRACE_INDEX_4 = 4;

    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;
    public static boolean IS_SHOW_LOG = true;
    public static final boolean IS_DEBUG_MODE = true;

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static void verbose() {
        printLog(VERBOSE, null, DEFAULT_MESSAGE);
    }

    public static void verbose(Object msg) {
        printLog(VERBOSE, null, msg);
    }

    public static void verbose(String tag, Object... objects) {
        printLog(VERBOSE, tag, objects);
    }

    public static void debug() {
        if(IS_DEBUG_MODE){

            printLog(DEBUG, null, DEFAULT_MESSAGE);
        }
    }

    public static void debug(Object msg) {
        if(IS_DEBUG_MODE){

            printLog(DEBUG, null, msg);
        }
    }

    public static void debug(String tag, Object... objects) {
        if(IS_DEBUG_MODE){

            printLog(DEBUG, tag, objects);
        }
    }

    public static void info() {
        printLog(INFO, null, DEFAULT_MESSAGE);
    }

    public static void info(Object msg) {
        printLog(INFO, null, msg);
    }

    public static void info(String tag, Object... objects) {
        printLog(INFO, tag, objects);
    }

    public static void warn() {
        printLog(WARN, null, DEFAULT_MESSAGE);
    }

    public static void warn(Object msg) {
        printLog(WARN, null, msg);
    }

    public static void warn(String tag, Object... objects) {
        printLog(WARN, tag, objects);
    }

    public static void error() {
        printLog(ERROR, null, DEFAULT_MESSAGE);
    }

    public static void error(Object msg) {
        printLog(ERROR, null, msg);
    }

    public static void error(String tag, Object... objects) {
        printLog(ERROR, tag, objects);
    }

    public static void a() {
        printLog(ASSERT, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(ASSERT, null, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(ASSERT, tag, objects);
    }

    public static void json(String jsonFormat) {
        printLog(JSON, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(XML, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(XML, tag, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    /*public static void debug() {
        printDebug(null, DEFAULT_MESSAGE);
    }

    public static void debug(Object msg) {
        printDebug(null, msg);
    }

    public static void debug(String tag, Object... objects) {
        printDebug(tag, objects);
    }*/

    public static void trace() {
        printStackTrace();
    }

    private static void printStackTrace() {

        if (!IS_SHOW_LOG) {
            return;
        }

        Throwable tr = new Throwable();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        String message = sw.toString();

        String traceString[] = message.split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String trace : traceString) {
            if (trace.contains("at com.socks.library.KLog")) {
                continue;
            }
            sb.append(trace).append("\n");
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString());
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(DEBUG, tag, headString + msg);
    }

    private static void printLog(int type, String tagStr, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case VERBOSE:
            case DEBUG:
            case INFO:
            case WARN:
            case ERROR:
            case ASSERT:
                BaseLog.printDefault(type, tag, headString + msg);
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
        }

    }
    
    private static void printLog(int type, String tagStr, Object objects) {
    	
    	if (!IS_SHOW_LOG) {
    		return;
    	}
    	
    	String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
    	String tag = contents[0];
    	String msg = contents[1];
    	String headString = contents[2];
    	
    	switch (type) {
    	case VERBOSE:
    	case DEBUG:
    	case INFO:
    	case WARN:
    	case ERROR:
    	case ASSERT:
    		BaseLog.printDefault(type, tag, headString + msg);
    		break;
    	case JSON:
    		JsonLog.printJson(tag, msg, headString);
    		break;
    	case XML:
    		XmlLog.printXml(tag, msg, headString);
    		break;
    	}
    	
    }

    private static void printDebug(String tagStr, Object... objects) {
    	if (!IS_SHOW_LOG) {
            return;
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(DEBUG, tag, headString + msg);
    }


    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = (tagStr == null ? className : tagStr);

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }

}

package com.huawei.zhanglong.zllog.log.logtype;

import android.util.Log;

import com.huawei.genexcloud.base.framework.log.GCLogger;
import com.huawei.genexcloud.base.framework.log.LoggerUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 
 */
public class JsonLog {

	public static void printJson(String tag, String msg, String headString) {

		String message;

		try {
			if (msg.startsWith("{")) {
				JSONObject jsonObject = new JSONObject(msg);
				message = jsonObject.toString(GCLogger.JSON_INDENT);
			} else if (msg.startsWith("[")) {
				JSONArray jsonArray = new JSONArray(msg);
				message = jsonArray.toString(GCLogger.JSON_INDENT);
			} else {
				message = msg;
			}
		} catch (JSONException e) {
			message = msg;
		}

		LoggerUtil.printLine(tag, true);
		message = headString + GCLogger.LINE_SEPARATOR + message;
		String[] lines = message.split(GCLogger.LINE_SEPARATOR);
		for (String line : lines) {
			Log.d(tag, "║ " + line);
		}
		LoggerUtil.printLine(tag, false);
	}
}

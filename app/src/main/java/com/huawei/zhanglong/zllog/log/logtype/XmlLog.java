package com.huawei.zhanglong.zllog.log.logtype;

import android.util.Log;

import com.huawei.genexcloud.base.framework.log.GCLogger;
import com.huawei.genexcloud.base.framework.log.LoggerUtil;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * @author jambestwick
 * date 2017-5-21
 */
public class XmlLog {

    public static void printXml(String tag, String xml, String headString) {

        if (xml != null) {
            xml = XmlLog.formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + GCLogger.NULL_TIPS;
        }

        LoggerUtil.printLine(tag, true);
        String[] lines = xml.split(GCLogger.LINE_SEPARATOR);
        for (String line : lines) {
            if (!LoggerUtil.isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }
        LoggerUtil.printLine(tag, false);
    }

    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }

}

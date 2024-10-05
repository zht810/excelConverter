package com.zht.exceldownloadplugin.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static String getVarValue(String template, Map<String, String> valueMap) {
        String pattern = "\\$\\{(.+?)\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            String value = getValue(valueMap, key);
            m.appendReplacement(sb, value);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String getValue(Map<String, String> valueMap, String key) {
        String value = valueMap.get(key);
        return StringUtils.isNotBlank(value) ? value : "";
    }

    public static void main1(String[] args) {
        Map map = new HashMap<>();
        map.put("name", "john");
        map.put("city", "beijing");
        String varValue = getVarValue("${  name  }", map);
        String varValue2 = getVarValue("Hello ${ name }, welcome to ${    city   }!", map);
        System.out.println(varValue);
        System.out.println(varValue2);
    }

    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("DATA_department", "UA.BIW.nomal.APIps01Audit001");
        String varValue2 = getVarValue("UA.BIW.nomal.APIps01Audit,${DATA_department}00900", map);
        System.out.println(varValue2);

    }
}

package com.zht.exceldownloadplugin.logic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zht.exceldownloadplugin.entity.ResultModel;
import com.zht.exceldownloadplugin.utils.RegexUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generate {

//    public static void main(String[] args) {
//         doReadAndWrite("/Users/mac/Desktop/test001.xlsx", "select * from table where exp_id='${exp_id}';", "/Users/mac/Desktop/");
//    }

    public static String doReadAndWrite(String sourceFile, String template, String targetPath) {
        try {
            return readAndWrite(sourceFile, template, targetPath);
        } catch (Exception e) {
            return "生成失败：" + e.getMessage();
        }
    }

    public static String readAndWrite(String sourceFile, String template, String targetPath) {
        // 校验
        String msg = check(sourceFile, template, targetPath);
        if (StringUtils.isNotBlank(msg)) {
            return msg;
        }

        // excel表头
        Map<Integer, String> headMap = new HashMap<>();
        // excel数据
        List<Map<Integer, String>> dataList = new ArrayList<>();

        // excel读操作
        readExcel(sourceFile, headMap, dataList);

        // 写回excel
        String result = writeResult(dataList, headMap, template, targetPath);
        return result;
    }

    private static void readExcel(String filename, Map<Integer, String> headMap, List<Map<Integer, String>> dataList) {
        EasyExcel.read(filename, new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {
                // 将读取到的每一行存入集合中
                dataList.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }

            @Override
            public void invokeHeadMap(Map<Integer, String> map, AnalysisContext context) {
                headMap.putAll(map);
            }

        }).sheet().doRead();
    }

    private static String writeResult(List<Map<Integer, String>> dataList,
                                      Map<Integer, String> headMap,
                                      String template, String targetPath) {
        List<ResultModel> list = new ArrayList<>();
        int total = 0;
        // dataMap=每一行数据，其中key=索引号
        for (Map<Integer, String> dataMap : dataList) {
            total++;
            // 每一行数据，key=字段名
            Map<String, String> columnMap = convertToHeaderName(headMap, dataMap);
            // 匹配模板
            String result = RegexUtils.getVarValue(template, columnMap);

            list.add(new ResultModel(result));
        }

        // 设置写入文件夹地址和excel文件名称
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss_SSS");
        String dateTimeStr = LocalDateTime.now().format(formatter);
        String path = targetPath + "/" + dateTimeStr + ".xlsx";

        EasyExcel.write(path, ResultModel.class).sheet("result").doWrite(list);

        return "总共生成" + total + "条数据。路径:" + path;
    }

    /**
     * 将map的key从索引号转成字段名
     *
     * @param headMap
     * @param dataMap
     * @return
     */
    private static Map<String, String> convertToHeaderName(Map<Integer, String> headMap, Map<Integer, String> dataMap) {
        Map<String, String> columnMap = new HashMap<>();
        for (Map.Entry<Integer, String> entity : dataMap.entrySet()) {
            Integer index = entity.getKey();
            String fieldValue = entity.getValue(); // 字段值
            String fieldName = headMap.get(index); // 字段名称

            if (StringUtils.isNotBlank(fieldName)) {
                columnMap.put(fieldName, fieldValue);
            }
        }
        return columnMap;
    }

    private static String check(String sourceFile, String template, String targetPath) {
        if (StringUtils.isBlank(sourceFile)) {
            return "请选择要处理的excel文件！";
        }
        if (StringUtils.isBlank(template) || template.contains("请输入模板")) {
            return "请输入模板！";
        }
        if (StringUtils.isBlank(targetPath)) {
            return "请输入要保存的路径！";
        }
        return null;
    }
}

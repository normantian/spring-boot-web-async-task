package com.norman.controller;

import com.google.common.collect.Lists;
import com.norman.annotation.ExcelExport;
import com.norman.annotation.ExcelExportField;
import com.norman.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/5/23 11:04 PM.
 */
@RestController
@Slf4j
@RequestMapping("/excel")
public class ExcelController {

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void resourceFinance(HttpServletResponse response, @RequestParam("path") String path) {
        // 下载文件的默认名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", System.currentTimeMillis() + ".xlsx"));

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
             OutputStream outputStream = response.getOutputStream()) {

            writeExcel(outputStream, inputStream, mockData(), Person.class);

        } catch (IOException ex) {
            log.error("export finance excel exception {}", ex.getMessage(), ex);
        }
    }

    private List<Person> mockData() {

        List<Person> personList = Lists.newArrayList(
                new Person(1, "田飞", 36),
                new Person(2, "芊芊", 36),
                new Person(3, "Lisa", 4)
        );
        return personList;


    }

    private <T> void writeExcel(OutputStream outputStream,
                                InputStream inputStream, List<T> data, Class<T> clazz) throws IOException {

        final ExcelExport excelExport = clazz.getAnnotation(ExcelExport.class);

        log.info("export info : {}, {}, {}", excelExport.sheetIndex(), excelExport.templateFileName(), excelExport.desc());
        StringBuilder stringBuilder = new StringBuilder();

        for (T t : data) {

            Arrays.stream(clazz.getDeclaredMethods()).forEach(System.out::println);

            Arrays.stream(clazz.getDeclaredFields()) //获得所有字段
                    .filter(field -> field.isAnnotationPresent(ExcelExportField.class)) //查找标记了注解的字段
                    .sorted(Comparator.comparingInt(a -> a.getAnnotation(ExcelExportField.class).cellIndex())) //根据注解中的cellIndex对字段排序
                    .peek(field -> field.setAccessible(true)) //设置可以访问私有字段
                    .forEach(field -> {
                        //获得注解
                        ExcelExportField excelExportField = field.getAnnotation(ExcelExportField.class);
                        Object value = "";
                        try {
                            //反射获取字段值
                            value = field.get(t);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        //根据字段类型以正确的填充方式格式化字符串
                        switch (excelExportField.type()) {
                            case "S": {
                                stringBuilder.append("\'" + value + "\' ");
                                break;
                            }
                            case "N": {
                                stringBuilder.append(value + " ");
                                break;
                            }
                            case "M": {
                                if (!(value instanceof BigDecimal)) {
                                    throw new RuntimeException(String.format("{} 的 {} 必须是BigDecimal", t, field));
                                }

                                stringBuilder.append(String.format("%0d", ((BigDecimal) value).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal("100")).longValue()));
                                break;
                            }
                            default:
                                break;
                        }

                    });
            stringBuilder.append("\n");
        }


        log.info("rows [" + stringBuilder.toString() + "]");

        return;

    }
}

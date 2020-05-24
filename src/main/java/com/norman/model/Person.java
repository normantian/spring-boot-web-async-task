package com.norman.model;

import com.norman.annotation.ExcelExport;
import com.norman.annotation.ExcelExportField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/5/23 11:17 PM.
 */
@Data
@AllArgsConstructor
@ExcelExport(sheetIndex = 1, desc = "test", templateFileName = "")
public class Person {

    @ExcelExportField(cellIndex = 1, type = "N")
    private int id;

    @ExcelExportField(cellIndex = 2, type = "S")
    private String name;

    @ExcelExportField(cellIndex = 3, type = "N")
    private int age;

    @ExcelExportField(cellIndex = 4, type = "N")
    public String getSex(){
        return age > 30 ? "F" : "M";
    }


}

package com.norman.model;

import com.norman.annotation.ExcelExport;
import com.norman.annotation.ExcelExportField;
import lombok.Data;

import java.util.Date;

@Data
@ExcelExport(sheetIndex = 1, desc = "test", templateFileName = "", startRow = 1)
public class Customer {
    @ExcelExportField(cellIndex = 0, type = "N")
    int id;
    @ExcelExportField(cellIndex = 1, type = "S")
    String name;
    @ExcelExportField(cellIndex = 2, type = "S")
    String email;
    Date date;

    public Customer(int id, String name, String email, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
    }
}

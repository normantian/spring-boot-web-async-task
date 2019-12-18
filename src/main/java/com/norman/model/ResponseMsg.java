package com.norman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2019/11/11 9:05 AM.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMsg<T> {
    private int code;

    private String msg;

    private T data;
}

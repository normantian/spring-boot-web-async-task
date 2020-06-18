package com.norman.model;

import lombok.Data;

/**
 * 通用response
 * @param <T>
 */
@Data
public class CommonResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}

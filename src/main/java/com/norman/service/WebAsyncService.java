package com.norman.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by tianfei on 2018/7/3.
 */
@Service
public class WebAsyncService {
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}

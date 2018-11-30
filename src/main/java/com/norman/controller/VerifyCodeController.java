package com.norman.controller;

import com.norman.util.CaptchaGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/30 3:55 PM.
 */
@RestController
@Slf4j
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    private static final String FORMAT_NAME = "JPG";

    private static final int CODE_COUNT = 4;

    @GetMapping(value = "/captcha/image")
    public ResponseEntity<InputStreamResource> getCaptchaImage() throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);

        String captcha = this.generateRandomString(CODE_COUNT, false);

        log.info("create captcha [{}]", captcha);

        final BufferedImage bufferedImage = CaptchaGenerator.generateCaptchaImage(captcha);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, FORMAT_NAME, os);

        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
        InputStreamResource isr = new InputStreamResource(inputStream);

        return new ResponseEntity<>(isr, responseHeaders, HttpStatus.OK);

    }

    private String generateRandomString(int length, boolean isNumber){
        return isNumber? RandomStringUtils.randomNumeric(length) :
                RandomStringUtils.randomAlphanumeric(length);

    }

}

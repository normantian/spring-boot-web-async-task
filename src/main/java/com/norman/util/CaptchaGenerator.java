package com.norman.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2018/10/30 4:08 PM.
 */
public abstract class CaptchaGenerator {
    // 验证码图片的宽度。
    private static int width = 150;
    // 验证码图片的高度。
    private static int height = 50;
    // 验证码字符个数
    private static int codeCount = 4;
    //
    private static int x = width / (codeCount + 2);

    private static String fontName= "Fixedsys";

    private static int fontHeight = height - 3;

    private static int codeY = height - 4;

    public static BufferedImage generateCaptchaImage(String captcha){
        char[]  codeSequence = captcha.toCharArray();
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font(fontName, Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);
        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 25; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12)+8;
            int yl = random.nextInt(12)+8;
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[i]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
        }
//        int w = buffImg.getWidth();
//        int h = buffImg.getHeight();
//        shear(g, w, h, Color.white);
        // 画边框。
//        g.setColor(Color.gray);
//        g.drawRect(0, 0, width - 1, height - 1);
        return buffImg;
    }
}

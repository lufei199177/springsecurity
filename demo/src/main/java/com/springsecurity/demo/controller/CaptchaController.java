package com.springsecurity.demo.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author lufei
 * @date 2020-10-18 17:39
 * @desc
 */
@Controller
public class CaptchaController {

    @Autowired
    private Producer captchaProducer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置内容类型
        response.setContentType("image/jpg");
        //创建验证码文本
        String capText = captchaProducer.createText();
        //将验证码文本设置到session
        request.getSession().setAttribute("captcha", capText);
        //创建验证码图片
        BufferedImage bufferedImage = captchaProducer.createImage(capText);
        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);

        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }
}

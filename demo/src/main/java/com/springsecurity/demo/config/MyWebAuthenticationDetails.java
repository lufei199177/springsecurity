package com.springsecurity.demo.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lufei
 * @date 2020/10/19
 * @desc
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imageCodeIsRight;
    private String imageCode;
    private String saveImageCode;

    public boolean isImageCodeIsRight() {
        return imageCodeIsRight;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getSaveImageCode() {
        return saveImageCode;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.imageCode=request.getParameter("captcha");
        HttpSession httpSession=request.getSession();
        this.saveImageCode= (String) httpSession.getAttribute("captcha");
        if(!StringUtils.isEmpty(saveImageCode)){
            httpSession.removeAttribute("captcha");
            if(!StringUtils.isEmpty(this.imageCode)&&this.imageCode.equals(this.saveImageCode)){
                this.imageCodeIsRight=true;
            }
        }
    }
}

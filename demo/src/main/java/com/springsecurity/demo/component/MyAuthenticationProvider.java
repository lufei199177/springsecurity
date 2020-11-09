package com.springsecurity.demo.component;

import com.springsecurity.demo.config.MyAuthenticationException;
import com.springsecurity.demo.config.MyWebAuthenticationDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lufei
 * @date 2020/10/19
 * @desc
 */
//@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    /*@Autowired
    private UserDetailsService userDetailsService;*/

    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {
        //编写更多校验逻辑

        //校验密码
        /*if(usernamePasswordAuthenticationToken.getCredentials()==null){
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","密码不能为空!"));
        }else {
            String presentedPassword=usernamePasswordAuthenticationToken.getCredentials().toString();
            if(!presentedPassword.equals(userDetails.getPassword())){
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","密码错误!"));
            }
        }*/
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) usernamePasswordAuthenticationToken.getDetails();
        //验证码不正确，抛异常
        if (!details.isImageCodeIsRight()) {
            throw new MyAuthenticationException();
        }
        //调用父类方法完成验证
        super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);
    }

    /*@Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {
        return this.userDetailsService.loadUserByUsername(s);
    }*/
}

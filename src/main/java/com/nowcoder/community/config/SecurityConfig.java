package com.nowcoder.community.config;

import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant
{
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        //忽略所有静态资源的权限控制
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //授权
        http.authorizeRequests()
                .antMatchers(
                        "/user/setting",    //用户设置
                        "/user/upload",                 //上传头像
                        "/discuss/add",                 //发帖
                        "/comment/add/**",              //发表评论
                        "/letter/**",                   //私信
                        "/notice/**",                    //通知
                        "/like",                        //赞
                        "/follow",                      //关注
                        "/unfollow"                     //取关

                )
                .hasAnyAuthority(
                        AUTHORITY_USER,
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR
                )
                .antMatchers("/discuss/top",
                        "/discuss/wonderful"
                )
                .hasAnyAuthority(
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )
                .anyRequest().permitAll()
                .and().csrf().disable();

        //权限不够时处理
        http.exceptionHandling()
                //没有登录时怎么处理
                .authenticationEntryPoint(new AuthenticationEntryPoint()
                {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException
                    {
                        String xRequestWith = request.getHeader("x-requested-with");
                        if("XMLHttpRequest".equals(xRequestWith))
                        {
                            //异步请求
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403,"你还没有登录！"));
                        }
                        else
                        {
                            response.sendRedirect(request.getContextPath() + "/login");
                        }
                    }
                })
                //登录了，权限不足时怎么处理
                .accessDeniedHandler(new AccessDeniedHandler()
                {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException
                    {
                        String xRequestWith = request.getHeader("x-requested-with");
                        if("XMLHttpRequest".equals(xRequestWith))
                        {
                            //异步请求
                            response.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(CommunityUtil.getJSONString(403,"你没有访问此功能的权限！"));
                        }
                        else
                        {
                            response.sendRedirect(request.getContextPath() + "/denied");
                        }
                    }
                });

        //Security底层默认会拦截/logout请求，进行退出处理
        //覆盖它默认的逻辑，才能进行自己的退出代码
        http.logout().logoutUrl("/securitylogout");
    }
}

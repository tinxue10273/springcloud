package provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import provider.common.JsonTool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static provider.common.CycleConstant.AUTHORITY_ADMIN;
import static provider.common.CycleConstant.AUTHORITY_MODERATOR;
import static provider.common.CycleConstant.AUTHORITY_USER;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers(
                       "/user/setting",
                       "/user/upload",
                       "/discuss/add",
                       "/comment/add/**",
                       "/letter/**",
                       "/notice/**",
                       "/like",
                       "/follow",
                       "/unfollow"
               )
               .hasAnyAuthority(
                       AUTHORITY_USER,
                       AUTHORITY_ADMIN,
                       AUTHORITY_MODERATOR
               )
               .antMatchers(
                       "/discuss/top",
                       "/discuss/wonderful"
               )
               .hasAnyAuthority(
                       AUTHORITY_MODERATOR
               )
               .antMatchers(
                       "/discuss/delete",
                       "/data/**",
                       "/actuator/**"
               )
               .hasAnyAuthority(
                       AUTHORITY_ADMIN
               )
               .anyRequest().permitAll()
               .and().csrf().disable();

       http.exceptionHandling()
               .authenticationEntryPoint(new AuthenticationEntryPoint() {
                   @Override
                   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                       String xRequestedWith = request.getHeader("x-requested-with");
                       if ("XMLHttpRequest".equals(xRequestedWith)) {
                           response.setContentType("application/plain;charset=utf-8");
                           PrintWriter writer = response.getWriter();
                           Map<Integer, String> map = new HashMap<>();
                           map.put(403, "请登录！");
                           writer.write(JsonTool.toJson(map));
                       } else {
                           response.sendRedirect(request.getContextPath() + "/login");
                       }
                   }
               })
               .accessDeniedHandler(new AccessDeniedHandler() {
                   @Override
                   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                       String xRequestedWith = request.getHeader("x-requested-with");
                       if ("XMLHttpRequest".equals(xRequestedWith)) {
                           response.setContentType("application/plain;charset=utf-8");
                           PrintWriter writer = response.getWriter();
                           Map<Integer, String> map = new HashMap<>();
                           map.put(403, "您没有当前功能的访问权限！");
                           writer.write(JsonTool.toJson(map));
                       } else {
                           response.sendRedirect(request.getContextPath() + "/login");
                       }
                   }
               });
       http.logout().logoutUrl("/securitylogout");
   }
}

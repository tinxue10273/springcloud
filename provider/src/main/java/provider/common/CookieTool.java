package provider.common;

import provider.response.BaseResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieTool {

    public static BaseResponse getValue(HttpServletRequest request, String name) {
        if (request == null || name == null) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("请求或用户名名字不能为空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return BaseResponse.builder().success(true).result(cookie.getValue()).build();
                }
            }
        }
        return CycleErrorCode.NOT_EXIST.getResponse(name + "的cookie");
    }
}

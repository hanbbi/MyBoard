package com.mybatis.myboard.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginUser_obj = session.getAttribute("loginUser");
        String uri = request.getRequestURI();   // URL : 서버 주소까지 포함
        String queryString = request.getQueryString();
        uri += (queryString != null) ? "?"+queryString : "";
        log.info("preHandle(url) : " + uri);
        if (loginUser_obj == null) {
            session.setAttribute("msg", "로그인 후 이용 가능한 서비스입니다.");
            session.setAttribute("redirectUri", uri);
            response.sendRedirect("/user/login.do");
            return false;
        }
        return true;
    }

    // 동적 페이지에서 view 를 랜더링하기 전
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle : " + modelAndView.getViewName());
    }

    // 동전 페이지에서 view 를 랜더링한 후, 요청 처리가 모두 끝나서 응답하기 직전
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // response.getWriter().append("<h1>ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ</h1>");
        log.info("afterCompletion");
    }
}

package com.mybatis.myboard.controller;

import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.UserDto;
import com.mybatis.myboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private Logger log = LoggerFactory.getLogger(getClass().getSimpleName());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list.do")
    public String list(PagingDto paging,
                       Model model,
                       HttpServletRequest req) {
        paging.setQueryString(req.getParameterMap());
        List<UserDto> userList = userService.listPaging(paging);
        log.info(paging.toString());
        model.addAttribute("userList", userList);
        model.addAttribute("paging", paging);

        return "/user/list";
    }

    @GetMapping("/detail.do")
    public ModelAndView detail(@RequestParam(name = "user_id") String userId,
                               ModelAndView model) throws IOException {
        UserDto user = userService.detail(userId);
        model.addObject("user", user);
        model.setViewName("/user/detail");
        return model;
    }

    @GetMapping("/modify.do")
    public String modify(@RequestParam(name = "user_id", required = false) String userId,
                         Model model,
                         @SessionAttribute UserDto loginUser) {
        if (userId != null) {
            UserDto user = userService.detail(userId);
            model.addAttribute("user", user);
            return "/user/modify";
        } else {
            return "redirect:/user/list.do";
        }
    }

    @PostMapping("/modify.do")
    public String modify(UserDto user,
                         @SessionAttribute UserDto loginUser) {
        int modify = 0;
        try {
            modify = userService.modify(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user);
        if (modify > 0) {   // 성공 시 상세 페이지로
            return "redirect:/user/detail.do?user_id=" + user.getUserId();
        } else {    // 실패 시 수정 페이지로
            return "redirect:/user/modify.do?user_id=" + user.getUserId();
        }
    }

    @GetMapping("/remove.do")
    public String remove(@RequestParam(name = "user_id") String userId,
                         @SessionAttribute UserDto loginUser) {
        int remove = 0;
        try {
            remove = userService.remove(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (remove > 0) {
            return "redirect:/user/list.do";
        } else {
            return "redirect:/user/modify.do?user_id=" + userId;
        }
    }

    @GetMapping("/signup.do")
    public void signup() {
    }

    @PostMapping("/signup.do")
    public String signup(UserDto user) {
        System.out.println(user);
        int signup = 0;
        try {
            signup = userService.signup(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (signup > 0) {
            return "redirect:/";
        } else {
            return "redirect:/user/signup.do";
        }
    }

    @GetMapping("/login.do")
    public void login(HttpServletRequest req,
                      HttpSession session,
                      @SessionAttribute(required = false) String redirectUri) {
        String referer = req.getHeader("referer");
        if (redirectUri == null && !(referer.equals("http://localhost:1109/") || referer.equals("http://localhost:1109/user/login.do"))) {
            session.setAttribute("redirectUri", referer);
        }
    }

    @PostMapping("/login.do")
    public String login(@RequestParam(name = "user_id") String userId,
                        String userPw,
                        HttpSession session,
                        @SessionAttribute(required = false) String redirectUri) {
        UserDto user = userService.login(userId, userPw);
        session.setAttribute("loginUser", user);
        if (user == null) {
            session.setAttribute("msg", "아이디 또는 비밀번호를 확인하세요.");
            return "redirect:/user/login.do";
        } else {
            if (redirectUri == null) return "redirect:/";
            session.removeAttribute("redirectUri");
            return "redirect:" + redirectUri;
        }
    }

    @GetMapping("/logout.do")
    public String logout(HttpSession session) {
        // session.invalidate();    // 전체
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

    @GetMapping("/find.do")
    public void find() {}

    @PostMapping("/find.do")
    public String find(@RequestParam(name = "user_id") String userId,
                       UserDto user) {
        UserDto findId = userService.detail(userId);
        int find = userService.idCheck(userId);
        if (find > 0) {
            return "redirect:/user/findpw.do?user_id=" + findId.getUserId();
        } else {
            return "redirect:/user/find.do";
        }
    }

    @GetMapping("/findpw.do")
    public void findpw(@RequestParam(name = "user_id") String userId,
                       Model model) throws IOException {
        UserDto user = userService.detail(userId);
        System.out.println(user);
        model.addAttribute("user", user);
    }
}

package com.mybatis.myboard.controller;

import com.mybatis.myboard.dto.AjaxStateHandler;
import com.mybatis.myboard.dto.PagingDto;
import com.mybatis.myboard.dto.ReplyDto;
import com.mybatis.myboard.dto.UserDto;
import com.mybatis.myboard.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private ReplyService replyService;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")    // 유저 간의 약속된 이미지 업로드 경로
    private String imgPath;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/{boardId}/list.do")
    public String list(@PathVariable int boardId,
                       PagingDto paging,
                       HttpServletRequest req,
                       Model model) {
        paging.setQueryString(req.getParameterMap());
        List<ReplyDto> replyList = replyService.boardDetailList(boardId, paging);
        model.addAttribute("replyList", replyList);
        model.addAttribute("paging", paging);
        return "/reply/list";
    }

    @DeleteMapping("/remove.do")
    public @ResponseBody AjaxStateHandler remove(int replyId,
                                                 @SessionAttribute UserDto loginUser) {
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        ReplyDto reply = replyService.detail(replyId);
        int remove = replyService.remove(replyId);
        ajaxStateHandler.setState(remove);
        if (remove > 0 && reply.getImgPath() != null) {
            File originImgFile = new File(imgPath + "/" + reply.getImgPath());
            boolean del = originImgFile.delete();
            log.info("원본 이미지 삭제 : ", del);
        }
        return ajaxStateHandler;
    }

    @PostMapping("/register.do")
    public @ResponseBody AjaxStateHandler register(ReplyDto reply,
                                                   @SessionAttribute UserDto loginUser,
                                                   MultipartFile imgFile) { // 임시 저장된 파일(blob)이 넘어온다.
        // MultipartFile input type = file name = imgFile 있으면 무조건 null 이 아니다.
        if (!imgFile.isEmpty()) {
            String[] contentsTypes = imgFile.getContentType().split("/");   // image/jpeg -> {"image", "jpeg"}
            if (contentsTypes[0].equals("image")) {
                String fileName = "reply_" + System.currentTimeMillis() + "_" + (int)(Math.random()*10000) + "." + contentsTypes[1];    // 회사 규칙대로 생성
                Path path = Paths.get(imgPath + "/" + fileName);
                try {
                    imgFile.transferTo(path);
                    reply.setImgPath(fileName);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        int register = 0;
        register = replyService.register(reply);
        ajaxStateHandler.setState(register);
        return ajaxStateHandler;
    }

    @GetMapping("/modify.do")
    public String modify(int replyId,
                         @SessionAttribute UserDto loginUser,
                         Model model) {
        ReplyDto reply = replyService.detail(replyId);
        model.addAttribute("reply", reply);
        return "/reply/modify";
    }

    @PutMapping("/modify.do")
    public @ResponseBody AjaxStateHandler modify(ReplyDto reply,
                                                 @SessionAttribute UserDto loginUser,
                                                 @RequestParam(required = false, name = "imgFile") MultipartFile imgFile) {
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        String originImgPath = reply.getImgPath();  // 없으면 null 또는 "" 으로 넘어옴

        if (imgFile != null && !imgFile.isEmpty()) {
            String[] contentTypes = imgFile.getContentType().split("/");
            if (contentTypes[0].equals("image")) {
                try {
                    String fileName = "reply_" + System.currentTimeMillis() + "_" + (int)(Math.random()*10000) + "." + contentTypes[1];    // 회사 규칙대로 생성
                    Path path = Paths.get(imgPath + "/" + fileName);
                    imgFile.transferTo(path);   // 임시 저장된 파일을 실제로 저장
                    reply.setImgPath(fileName);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }

        int modify = replyService.modify(reply);
        if (modify > 0 && (originImgPath != null || !originImgPath.isEmpty())) {
            File originImgFile = new File(imgPath + "/" + originImgPath);
            boolean del = originImgFile.delete();
            log.info("원본 이미지 삭제: ", del);
        }
        ajaxStateHandler.setState(modify);
        return ajaxStateHandler;
    }
}

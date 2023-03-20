package com.mybatis.myboard.controller;

import com.mybatis.myboard.dto.AjaxStateHandler;
import com.mybatis.myboard.dto.BoardPreferDto;
import com.mybatis.myboard.dto.BoardPreferViewDto;
import com.mybatis.myboard.dto.UserDto;
import com.mybatis.myboard.service.BoardPreferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping("/board/prefer/")
public class BoardPreferController {
    private BoardPreferService boardPreferService;

    public BoardPreferController(BoardPreferService boardPreferService) {
        this.boardPreferService = boardPreferService;
    }

    @GetMapping("/view.do")
    public String view(
            int boardId,
            @SessionAttribute(required = false) UserDto loginUser,
            Model model) {
        String loginUserId = (loginUser != null) ? loginUser.getUserId() : null;
        BoardPreferViewDto boardPreferView = boardPreferService.detailBoardPreferView(boardId, loginUserId);
        model.addAttribute("prefer", boardPreferView);
        return "/board/boardPrefer";
    }

    @RequestMapping(method = {GET, PUT}, path = "/handler.do")
    public @ResponseBody AjaxStateHandler handler(
            int boardId,
            boolean preferBtn,
            @SessionAttribute UserDto loginUser) {
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        BoardPreferDto userLoginPrefer = boardPreferService.detail(boardId, loginUser.getUserId());
        int handler = 0;
        if (userLoginPrefer == null) { //한번도 좋아요 싫어요를 한적 업는 경우 => 등록
            userLoginPrefer = new BoardPreferDto();
            userLoginPrefer.setBoardId(boardId);
            userLoginPrefer.setUserId(loginUser.getUserId());
            userLoginPrefer.setPrefer(preferBtn);
            handler = boardPreferService.register(userLoginPrefer);
        } else {
            if (userLoginPrefer.isPrefer() == preferBtn) {//좋아요(싫어요)를 했는데 다시 좋아요(싫어요)를 하는 경우 =>삭제
                handler = boardPreferService.remove(userLoginPrefer.getBoardPreferId());
            } else {
                userLoginPrefer.setPrefer(preferBtn);
                handler = boardPreferService.modify(userLoginPrefer);
            }
        }
        ajaxStateHandler.setState(handler);
        return ajaxStateHandler;
    }
}

package com.mybatis.myboard.controller;

import com.mybatis.myboard.ImgFileUploadUtil;
import com.mybatis.myboard.dto.*;
import com.mybatis.myboard.service.BoardPreferService;
import com.mybatis.myboard.service.BoardService;
import com.mybatis.myboard.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private ReplyService replyService;
    private BoardPreferService boardPreferService;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")
    private String imgPath;

    public BoardController(BoardService boardService, ReplyService replyService, BoardPreferService boardPreferService) {
        this.boardService = boardService;
        this.replyService = replyService;
        this.boardPreferService = boardPreferService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req) {
        if (paging.getOrderField() == null) paging.setOrderField("board_id");
        paging.setQueryString(req.getParameterMap());
        List<BoardDto> boardList = boardService.listPaging(paging);
        model.addAttribute("boardList", boardList);
        model.addAttribute("paging", paging);

        return "/board/list";
    }

    @GetMapping("/{boardId}/detail.do")
    public String detail(
            @PathVariable int boardId,
            PagingDto paging,
            HttpServletRequest req,
            Model model,
            @SessionAttribute(required = false) UserDto loginUser) {
        paging.setQueryString(req.getParameterMap());
        BoardDto board = boardService.detail(boardId);
        List<ReplyDto> replyList = replyService.boardDetailList(boardId, paging);
        if (loginUser != null) {
            BoardPreferDto loginUserPrefer = boardPreferService.detail(boardId, loginUser.getUserId());
            board.getBoardPreferView().setLoginUserPrefer(loginUserPrefer);
        }

        model.addAttribute("board", board);
        model.addAttribute("replyList", replyList);
        model.addAttribute("paging", paging);

        return "/board/detail";
    }

    @GetMapping("/register.do")
    public void register(@SessionAttribute UserDto loginUser) {
    }

    @PostMapping("/register.do")
    public String register(BoardDto board,
                           @SessionAttribute UserDto loginUser,
                           @RequestParam(required = false, name = "imgFile") MultipartFile[] imgFiles) {
        int register = 0;
        if (loginUser.getUserId().equals(board.getUserId())) {
            try {
                List<BoardImgDto> boardImgList = new ArrayList<BoardImgDto>();
                for (MultipartFile imgFile : imgFiles) {
                    if (imgFile != null && !imgFile.isEmpty()) {
                        String[] contentTypes = imgFile.getContentType().split("/");
                        if (contentTypes[0].equals("image")) {
                            try {
                                String fileName = "board_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                                Path path = Paths.get(imgPath + "/" + fileName);
                                imgFile.transferTo(path);
                                BoardImgDto boardImg = new BoardImgDto();
                                boardImg.setImgPath(fileName);
                                boardImgList.add(boardImg);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
                board.setBoardImgList(boardImgList);
                register = boardService.register(board);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (register > 0) {
            return "redirect:/board/" + board.getBoardId() + "/detail.do";
        } else {
            return "redirect:/board/register.do";
        }
    }

    @GetMapping("/{boardId}/modify.do")
    public String modify(@PathVariable int boardId,
                         Model model,
                         @SessionAttribute UserDto loginUser) {
        BoardDto board = boardService.detail(boardId);
        model.addAttribute("board", board);
        return "/board/modify";
    }

    @PostMapping("/modify.do")
    public String modify(BoardDto board,
                         @SessionAttribute UserDto loginUser,
                         @RequestParam(required = false, name = "delImgId") int[] delImgIds,
                         @RequestParam(required = false, name = "imgFile") MultipartFile[] imgFiles) {
        int modify = 0;
        List<String> saveFileNames = new ArrayList<String>(); //저장 실패시 삭제용
        List<BoardImgDto> delBoardImgList = null;
        log.info(board.toString());
        if (loginUser.getUserId().equals(board.getUserId())) {
            try {
                List<BoardImgDto> boardImgList = new ArrayList<BoardImgDto>();
                for (MultipartFile imgFile : imgFiles) {
                    String saveFileName = ImgFileUploadUtil.save(imgFile, imgPath, "board");
                    if (saveFileName != null) {
                        saveFileNames.add(saveFileName);
                        BoardImgDto boardImg = new BoardImgDto();
                        boardImg.setImgPath(saveFileName);
                        log.info(boardImg.toString());
                        boardImgList.add(boardImg);
                    }
                }
                board.setBoardImgList(boardImgList);
                delBoardImgList = boardService.modify(board, delImgIds); //수정 성공하면 삭제할 이미지 반환됨
                modify = 1;
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                int delImgCount = ImgFileUploadUtil.remove(imgPath, saveFileNames);
                log.error("삭제된 이미지 :" + delImgCount);
            }
        }

        if (modify > 0) {
            try {
                if (delBoardImgList != null) {//수정할 때 반환받은 삭제할 이미지 리스트로 삭제
                    for (BoardImgDto delBoardImg : delBoardImgList) {
                        ImgFileUploadUtil.remove(imgPath, delBoardImg.getImgPath());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return "redirect:/board/" + board.getBoardId() + "/detail.do";
        } else {
            return "redirect:/board/" + board.getBoardId() + "/modify.do";
        }
    }

    @GetMapping("/{boardId}/remove.do")
    public String remove(@PathVariable int boardId,
                         HttpSession session,
                         Model model,
                         @SessionAttribute UserDto loginUser,
                         @SessionAttribute(required = false) String msg) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }

        int remove = 0;
        try {
            remove = boardService.remove(boardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (remove > 0) {
            session.setAttribute("msg","게시글 삭제에 성공하였습니다.");
            return "redirect:/board/list.do";
        } else {
            session.setAttribute("msg","게시글 삭제에 실패하였습니다. 다시 시도해 주세요.");
            return "redirect:/board/" + boardId + "/modify.do";
        }
    }
}

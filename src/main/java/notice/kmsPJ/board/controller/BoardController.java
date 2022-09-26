package notice.kmsPJ.board.controller;


import notice.kmsPJ.board.entity.Board;
import notice.kmsPJ.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/board/write")
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritepro(Board board, Model model, MultipartFile file) throws Exception{


        if (board.getTitle().isBlank()) {
            model.addAttribute("searchUrl", "/board/write");
            model.addAttribute("message", "글 제목을 채워주세요."); // message를 보내고 어떻게 기존 텍스트를 유지할수있나...?
            return "message";
        } else if (board.getContent().isBlank()) {
            model.addAttribute("searchUrl", "/board/write");
            model.addAttribute("message", "글 내용을 채워주세요.");
            return "message";
        } else {
            model.addAttribute("searchUrl", "/board/list");
            model.addAttribute("message", "글 작성이 완료되었습니다.");
            boardService.save_context(board, file);
            return "message";
        }
    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 9, 1);
        int endPage = Math.min(nowPage + 10, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist"; //URL
    }

    @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardview(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.BoardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
    }
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id")Integer id, Board board, Model model, MultipartFile file) throws IOException {
        model.addAttribute("board", boardService.boardUpdate(board));
        model.addAttribute("searchUrl", "/board/list");
        model.addAttribute("message", "수정이 되었습니다.");
        boardService.save_context(board,file); //DB에 저장을 해야제.
        return "message";
    }
}

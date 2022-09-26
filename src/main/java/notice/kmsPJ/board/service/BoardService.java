package notice.kmsPJ.board.service;

import notice.kmsPJ.board.entity.Board;
import notice.kmsPJ.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    //글 작성 저장
    public void save_context(Board board, MultipartFile file) throws IOException { //파일 받는 메소드

        String projectPath = System.getProperty("user.dir") /*프로젝트 경로를 저장하는것*/ + "\\src\\main\\resources\\static\\file";

        UUID uuid = UUID.randomUUID(); //랜덤 식별자 이름을 설정함.
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        //SQL에다가 넣기
        board.setFilename(fileName);
        board.setFilepath("/file/"+fileName);
        boardRepository.save(board);
    }

    //글 리스트
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public Board boardview(Integer id) {
        Optional<Board> byId = boardRepository.findById(id);
        return byId.get();
    }

    //특정 게시글 삭제
    public void BoardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    //특정 게시글 업데이트
    public Board boardUpdate(Board board) {
        Board updateBoard = boardRepository.findById(board.getId()).get();
        updateBoard.setContent(board.getContent());
        updateBoard.setTitle(board.getTitle());

        return updateBoard;
    }
}

package com.toyota.rentalcar.dev.Board.service;

import com.toyota.rentalcar.dev.Board.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.FileSaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.ReplySaveRequestDto;
import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardType;
import com.toyota.rentalcar.dev.Board.model.File;
import com.toyota.rentalcar.dev.Board.model.Reply;
import com.toyota.rentalcar.dev.Board.repositories.BoardRepository;
import com.toyota.rentalcar.dev.Board.repositories.FileRepository;
import com.toyota.rentalcar.dev.Board.repositories.ReplyRepository;
import com.toyota.rentalcar.dev.Board.vo.PageVO;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.commons.exceptions.ApiException;
import com.toyota.rentalcar.dev.commons.exceptions.BadRequestException;
import com.toyota.rentalcar.dev.commons.model.ApiExceptionData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final FileRepository  fileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    /***
     * 파일이 없는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @return boardId
     */
    @Transactional
    public void saveArticle(@Valid BoardSaveRequestDto requestDto) throws ApiException{
        try {
            Board board = requestDto.toEntity();
            board.setUserPass(board.getUserPass());
            boardRepository.save(board);
        } catch (Exception e){
            throw new ApiException("INVALID_ARTICLE_POST", "게시글 형식이 잘못되었습니다.", new ApiExceptionData().add("board", requestDto));
        }
    }

    /***
     * 파일이 있는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @return
     */
    @Transactional
    public void saveArticleWithFiles(@Valid BoardSaveRequestDto requestDto) {

        requestDto.setUserPass(passwordEncoder.encode(requestDto.getUserPass()));

        Board board = requestDto.toEntity();

        if (requestDto.getFiles() != null) {
            List<String> files = requestDto.getFiles();
            List<File> entities = new ArrayList<>();

            for (String file : files) {
                FileSaveRequestDto dto = new FileSaveRequestDto();
                dto.setFileName(file);
                dto.setBoard(board);
                entities.add(dto.toEntity());
            }
            fileRepository.saveAll(entities);
            boardRepository.save(board);
        }
        boardRepository.save(board);
    }

    @Transactional
    public Page<Board> pagination(PageVO vo, Pageable page){
        return boardRepository.findAll(boardRepository.makePredicate(vo.getType(), vo.getKeyword()), page);
    }

    @Transactional
    public boolean tryToUpdateArticle(long id, String password) throws ApiException {
        Optional<Board> optBoard = boardRepository.findById(id);
        if(optBoard.isPresent()){
            return optBoard.get().passwordCheckWithPasswordEncoder(passwordEncoder, password);
        } else {
            throw new ApiException("INVALID_BOARD_ID", "게시글 번호를 잘못 입력하였습니다.", new ApiExceptionData().add("board_id", id));
        }
    }

    @Transactional
    public boolean tryToUpdateReply(long replyId, String password) throws ApiException {
        Optional<Reply> optReply = replyRepository.findById(replyId);
        if(optReply.isPresent()){
            return optReply.get().passwordCheckWithPasswordEncoder(passwordEncoder, password);
        } else {
            throw new ApiException("INVALID_REPLY_ID", "댓글 번호를 잘못 입력하셨습니다.", new ApiExceptionData().add("reply_id", replyId));
        }
    }

    @Transactional
    public Board getArticleById(Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);
        if(maybeBoard.isPresent()) {
            maybeBoard.get().updateViewHit(1);
            return maybeBoard.get();
        }
        throw new BadRequestException(id + " 해당 게시글을 찾을 수가 없습니다.");
    }

    @Transactional
    public Board updateArticleType(Long id, BoardType type) throws ApiException {
        Optional<Board> maybeBoard = boardRepository.findById(id);

        if(maybeBoard.isPresent()) {
            maybeBoard.get().updateBoardType(type);
            return maybeBoard.get();
        }
        throw new ApiException("INVALID_BOARD_ID", "해당 게시글을 찾을 수가 없습니다.", new ApiExceptionData().add("board_id", id));
    }

    @Transactional
    public ResponseEntity<List<Reply>> addReply(Long boardId, ReplySaveRequestDto requestDto){
        Board board = boardRepository.getOne(boardId);

        requestDto.setBoard(board);
        Reply reply = requestDto.toEntity();
        reply.setUserPass(reply.getUserPass());

        board.addReply(reply);
        replyRepository.save(reply);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    @Transactional
    public List<Reply> getReplyListById(long id) throws ApiException {
        List<Reply> maybeReply = replyRepository.findAllByBoard_IdOrderByIdAsc(id);
        if(maybeReply.size() != 0){
            return maybeReply;
        }
        throw new ApiException("NOT_FOUND", "해당 게시글에 댓글이 조회되지 않습니다.", new ApiExceptionData().add("reply_id", id));
    }

    @Transactional
    public Reply getReplyByBoardIdAndReplyId(long boardId, long replyId) throws ApiException {
        Optional<Reply> optReply = replyRepository.findByBoard_IdAndId(boardId, replyId);
        if(optReply.isPresent()){
            return optReply.get();
        }
        throw new ApiException("NOT_FOUND", "해당 게시글에 댓글이 조회되지 않습니다.", new ApiExceptionData().add("reply_id", replyId));
    }

    private List<Reply> getListByBoard(Board board) throws RuntimeException {
        logger.info("getListByBoard...."  + board);
        return replyRepository.findAllByBoard_IdOrderByIdAsc(board.getId());
    }

    @Transactional
    public ResponseEntity<List<Reply>> removeReply(Long boardId, Long replyId){
        logger.info("delete reply : " + replyId);

        replyRepository.deleteById(replyId);

        Board board = boardRepository.getOne(boardId);
        boardRepository.findById(boardId).map(
                result -> passwordEncoder.matches(result.getUserPass(), "test")
        ).orElse(false);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateReply(Long boardId, Long replyId, ReplySaveRequestDto updateRequest) throws ApiException {
        Board board = boardRepository.getOne(boardId);

        try {
                replyRepository.findById(replyId).ifPresent(reply -> {
                reply.update(updateRequest.getContent(), updateRequest.getUserName(), updateRequest.getUserPass(), board);
                replyRepository.save(reply);
            });
        } catch (Exception e){
            throw new ApiException("INVALID_REPLY_ID", "댓글 id가 잘못되었습니다.", new ApiExceptionData().add("cause", e.getCause()));
        }
        return ResponseEntity.accepted().body(new ApiResponse(true, "댓글이 정상적으로 수정되었습니다."));
    }

    public ResponseEntity<?> deleteArticle(Long id) throws ApiException {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e){
            throw new ApiException("INVALID_BOARD_ID", "게시글 id가 잘못되었습니다.", new ApiExceptionData().add("reply_id", id));
        }
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 삭제되었습니다."));
    }

    public ResponseEntity<?> updateArticle(Long id, BoardSaveRequestDto updateRequest) {
        try {
            boardRepository.findById(id).ifPresent(article -> {
                article.updateArticle(updateRequest.toEntity());

                if(updateRequest.getFiles() != null){
                    List<File> originFiles = article.getFiles();
                    originFiles.addAll(article.getFiles());

                    List<File> uniqueFiles = new ArrayList<File>(new HashSet<File>(originFiles));

                    fileRepository.findAllByBoard_Id(id).ifPresent(files -> {
                        uniqueFiles.retainAll(files);
                        for (File file : uniqueFiles) {
                            file.updateBoardFK(article);
                        }
                        fileRepository.saveAll(uniqueFiles);
                    });
                }
                boardRepository.save(article);
            });
            return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 수정되었습니다."));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

    }
}



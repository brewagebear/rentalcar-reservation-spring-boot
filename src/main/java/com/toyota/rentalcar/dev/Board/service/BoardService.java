package com.toyota.rentalcar.dev.Board.service;

import com.toyota.rentalcar.dev.Board.dto.BoardRequestDto;
import com.toyota.rentalcar.dev.Board.dto.FileRequestDto;
import com.toyota.rentalcar.dev.Board.dto.ReplyRequestDto;
import com.toyota.rentalcar.dev.Board.model.*;
import com.toyota.rentalcar.dev.Board.repositories.BoardRepository;
import com.toyota.rentalcar.dev.Board.repositories.FileRepository;
import com.toyota.rentalcar.dev.Board.repositories.ReplyRepository;
import com.toyota.rentalcar.dev.Board.vo.PageVO;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.commons.exceptions.ApiException;
import com.toyota.rentalcar.dev.commons.model.ApiExceptionData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final FileRepository  fileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    /**
     * 파일이 없는 게시글 저장 메소드
     * @throws ApiException
     * @Author Seansin(nalpum1993@gmail.com)
     */
    @Transactional
    public ResponseEntity<ApiResponse> saveArticle(BoardRequestDto requestDto, boolean isNotice) throws ApiException{
        try {
            Board board = requestDto.toEntity();
            board.setUserPass(board.getUserPass());
            board.setBoardCategory(isNotice);
            boardRepository.save(board);
        } catch (Exception e){
            throw new ApiException("INVALID_ARTICLE_POST", "게시글 형식이 잘못되었습니다.", new ApiExceptionData().add("board", requestDto));
        }
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 등록되었습니다."));
    }

    /**
     * 파일이 있는 게시글 저장 메소드
     */
    @Transactional
    public ResponseEntity<ApiResponse> saveArticleWithFiles(BoardRequestDto requestDto, boolean isNotice) {
        Board board = requestDto.toEntity();
        board.setUserPass(requestDto.getUserPass());

        if (requestDto.getFiles() != null) {
            List<String> files = requestDto.getFiles();
            List<File> entities = new ArrayList<>();

            for (String file : files) {
                FileRequestDto dto = new FileRequestDto();
                dto.setFileName(file);
                dto.setBoard(board);
                entities.add(dto.toEntity());
            }
            fileRepository.saveAll(entities);
        }
        board.setBoardCategory(isNotice);
        boardRepository.save(board);
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 등록되었습니다."));
    }

    @Transactional
    public List<Board> getFixedHeaderQnaArticle() {
        return boardRepository.findByBoardCategoryAndBoardType(BoardCategory.QNA, BoardType.FIXED_HEADER);
    }

    @Transactional
    public List<Board> getFixedHeaderNoticeArticle() {
        return boardRepository.findByBoardCategoryAndBoardType(BoardCategory.NOTICE, BoardType.FIXED_HEADER);
    }

    @Transactional(readOnly = true)
    public Page<Board> noticePagination(PageVO vo, Pageable page){
        Map<String, Object> filter = new HashMap<>();
            if(vo.getType() != null && vo.getKeyword() != null) {
                filter.put(vo.getType(), vo.getKeyword());
                return boardRepository.findAll(
                        Specification.where(BoardSpecification.getArticleByCategory(BoardCategory.NOTICE))
                                .and(BoardSpecification.getPredicateWithKeyword(filter))
                        , page);
            } else {
            return boardRepository.findAll(
                    Specification.where(BoardSpecification.getArticleByCategory(BoardCategory.NOTICE)), page);
        }
    }

    @Transactional(readOnly = true)
    public Page<Board> qnaPagination(PageVO vo, Pageable page){
        Map<String, Object> filter = new HashMap<>();
        if(vo.getType() != null && vo.getKeyword() != null) {
            filter.put(vo.getType(), vo.getKeyword());
            return boardRepository.findAll(
                    Specification.where(BoardSpecification.getArticleByCategory(BoardCategory.QNA))
                            .and(BoardSpecification.getPredicateWithKeyword(filter))
                    , page);
        } else {
            return boardRepository.findAll(
                    Specification.where(BoardSpecification.getArticleByCategory(BoardCategory.QNA)), page);
        }
    }

    @Transactional
    public Map<String, Object> getArticleById(Long id) throws ApiException {
        Optional<Board> optBoard = boardRepository.findById(id);
        Optional<Board> previousArticle = boardRepository.findFirstByIdIsLessThanOrderByIdDesc(id);
        Optional<Board> nextArticle = boardRepository.findFirstByIdIsGreaterThanOrderByIdAsc(id);

        Map<String , Object> resultMap = new HashMap<>();
        Map<String, String> smallPagination = new HashMap<>();

        return optBoard.map(article -> {
            previousArticle.map(previous -> {
                smallPagination.put("previous-article-id", previous.getId().toString());
                return smallPagination;
            }).orElseGet( () -> {
                smallPagination.put("previous-article-id", null);
                return smallPagination;
            });

            nextArticle.map(next -> {
                smallPagination.put("next-article-id", next.getId().toString());
                return smallPagination;
            }).orElseGet( () -> {
                smallPagination.put("next-article-id", null);
                return smallPagination;
            });

            resultMap.put("currentArticle", article);
            resultMap.put("previousArticle", smallPagination.get("previous-article-id"));
            resultMap.put("nextArticle", smallPagination.get("next-article-id"));
            article.updateViewHit(1);
            return resultMap;
        }).orElseThrow(ApiException::new);
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateArticleType(Long id, BoardType type) throws ApiException {
        Optional<Board> optBoard = boardRepository.findById(id);

        if(optBoard.isPresent()) {
            optBoard.get().updateBoardType(type);
            return ResponseEntity.ok().body(new ApiResponse(true, "게시판 타입이 정상적으로 수정되었습니다."));
        }
        throw new ApiException("INVALID_BOARD_ID", "해당 게시글을 찾을 수가 없습니다.", new ApiExceptionData().add("board_id", id));
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

    public ResponseEntity<ApiResponse> updateArticle(Long id, BoardRequestDto updateRequest) {
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
            return ResponseEntity.badRequest().body(new ApiResponse(false, "수정에 실패하였습니다", e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse> deleteArticle(Long id) throws ApiException {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e){
            throw new ApiException("INVALID_BOARD_ID", "게시글 id가 잘못되었습니다.", new ApiExceptionData().add("reply_id", id));
        }
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 삭제되었습니다."));
    }

    @Transactional
    public ResponseEntity<List<Reply>> addReply(Long boardId, ReplyRequestDto requestDto){
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
    public boolean tryToUpdateReply(long replyId, String password) throws ApiException {
        Optional<Reply> optReply = replyRepository.findById(replyId);
        if(optReply.isPresent()){
            return optReply.get().passwordCheckWithPasswordEncoder(passwordEncoder, password);
        } else {
            throw new ApiException("INVALID_REPLY_ID", "댓글 번호를 잘못 입력하셨습니다.", new ApiExceptionData().add("reply_id", replyId));
        }
    }

    @Transactional
    public ResponseEntity<?> updateReply(Long boardId, Long replyId, ReplyRequestDto updateRequest) throws ApiException {
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

    // 수정해야함
    @Transactional
    public ResponseEntity<List<Reply>> removeReply(Long boardId, Long replyId){
        replyRepository.deleteById(replyId);

        Board board = boardRepository.getOne(boardId);
        boardRepository.findById(boardId).map(
                result -> passwordEncoder.matches(result.getUserPass(), "test")
        ).orElse(false);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }

}




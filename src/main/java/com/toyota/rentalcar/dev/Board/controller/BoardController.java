package com.toyota.rentalcar.dev.Board.controller;


import com.toyota.rentalcar.dev.Board.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.ReplySaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.passwordForm;
import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardType;
import com.toyota.rentalcar.dev.Board.model.Reply;
import com.toyota.rentalcar.dev.Board.service.BoardService;
import com.toyota.rentalcar.dev.Board.vo.PageMaker;
import com.toyota.rentalcar.dev.Board.vo.PageVO;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.commons.exceptions.ApiException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @ApiOperation(value = "게시글 등록 API")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody BoardSaveRequestDto requestDto) throws ApiException {
        if(!requestDto.getFiles().isEmpty()){
            boardService.saveArticleWithFiles(requestDto);
            return ResponseEntity.accepted().body(new ApiResponse(true, "글이 성공적으로 등록되었습니다."));
        }
        boardService.saveArticle(requestDto);
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 등록되었습니다."));
    }

    @ApiOperation(value = "게시글 타입 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "board_type", value = "게시글 타입 (상단고정인지 아닌지)", required = true, dataType = "string")
    })
    @PutMapping("/{board_id}/{board_type}")
    public ResponseEntity<?> updateBoardType(@PathVariable("board_id")Long id, @PathVariable("board_type") BoardType type) throws ApiException {
        return ResponseEntity.ok().body(boardService.updateArticleType(id, type));
    }

    @ApiOperation(value = "게시글 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @PutMapping("/{board_id}")
    public ResponseEntity<?> updateBoard(@PathVariable("board_id")Long boardId, @Valid @RequestBody BoardSaveRequestDto updateRequestDto){
        return boardService.updateArticle(boardId, updateRequestDto);
    }

    @ApiOperation(value = "게시글 수정 전 비밀번호 체크 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @PostMapping("/{board_id}")
    public ResponseEntity<?> comparePasswordInArticle(@PathVariable("board_id")Long boardId, @RequestBody passwordForm requestDto) throws ApiException {
        if(boardService.tryToUpdateArticle(boardId, requestDto.getPassword())){
            return viewBoardDetail(boardId);
        }
        throw new ApiException("INVALID_PASSWORD", "잘못된 비밀번호를 입력하셨습니다.");
    }

    @ApiOperation(value = "게시글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @DeleteMapping("/{board_id}")
    public ResponseEntity<?> updateBoard(@PathVariable("board_id")Long boardId) throws ApiException {
        return boardService.deleteArticle(boardId);
    }

    @ApiOperation(value = "페이지네이션 처리 API")
    @GetMapping(value = "")
    public PageMaker list(PageVO vo){
        Pageable page = vo.makePageable(0, "id");
        Page<Board> result = boardService.pagination(vo, page);
        return new PageMaker(result);
    }

    @ApiOperation(value = "글 상세보기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @GetMapping(value = "/{board_id}")
    public ResponseEntity<?> viewBoardDetail(@PathVariable("board_id")Long boardId) throws ApiException {
        return ResponseEntity.ok().body(boardService.getArticleById(boardId));
    }

    @ApiOperation(value = "댓글 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @PostMapping(value = "{board_id}/reply")
    public ResponseEntity<?> addReply(@PathVariable("board_id")Long boardId, @RequestBody ReplySaveRequestDto requestDto){
        return boardService.addReply(boardId, requestDto);
    }

    @ApiOperation(value = "게시글에 달린 댓글 리스트 보기 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long")
    })
    @GetMapping(value = "/{board_id}/reply")
    private ResponseEntity<?> viewReplyList(@PathVariable("board_id")Long boardId) throws ApiException {
        return ResponseEntity.ok().body(boardService.getReplyListById(boardId));
    }

    @ApiOperation(value = "댓글 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "reply_id", value = "댓글 id", required = true, dataType = "long")
    })
    @GetMapping(value = "/{board_id}/reply/{reply_id}")
    private ResponseEntity<?> viewReplyOne(@PathVariable("board_id")Long boardId, @PathVariable("reply_id")Long replyId) throws ApiException {
        return ResponseEntity.ok().body(boardService.getReplyByBoardIdAndReplyId(boardId, replyId));
    }

    @ApiOperation(value = "댓글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "reply_id", value = "댓글 id", required = true, dataType = "long")
    })
    @DeleteMapping(value = "/{board_id}/reply/{reply_id}")
    public ResponseEntity<?> removeReply(@PathVariable("board_id")Long boardId, @PathVariable("reply_id")Long replyId){
        return boardService.removeReply(boardId, replyId);
    }

    @ApiOperation(value = "댓글 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "reply_id", value = "댓글 id", required = true, dataType = "long")
    })
    @PutMapping(value = "/{board_id}/reply/{reply_id}")
    public ResponseEntity<?> updateReply(@PathVariable("board_id")Long boardId,
                                         @PathVariable("reply_id")Long replyId,
                                         @Valid @RequestBody ReplySaveRequestDto updateRequest) throws ApiException {
        return boardService.updateReply(boardId, replyId, updateRequest);
    }

    @ApiOperation(value = "댓글 수정 전 비밀번호 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "board_id", value = "게시글 id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "reply_id", value = "댓글 id", required = true, dataType = "long")
    })
    @PostMapping("/{board_id}/reply/{reply_id}")
    public ResponseEntity<?> comparePasswordInReply(@PathVariable("board_id") Long boardId,
                                                    @PathVariable("reply_id") Long replyId,
                                                    @RequestBody passwordForm requestDto) throws ApiException {
        if(boardService.tryToUpdateReply(replyId, requestDto.getPassword())){
            return viewReplyOne(boardId, replyId);
        }
        throw new ApiException("INVALID_PASSWORD", "잘못된 비밀번호를 입력하셨습니다.");
    }
}

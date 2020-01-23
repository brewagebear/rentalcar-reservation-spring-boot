package com.toyota.rentalcar.dev.Board.controller;


import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardType;
import com.toyota.rentalcar.dev.Board.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.RentalCar.dto.payload.ApiResponse;
import com.toyota.rentalcar.dev.Board.service.BoardService;
import com.toyota.rentalcar.dev.Board.vo.PageMaker;
import com.toyota.rentalcar.dev.Board.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody BoardSaveRequestDto requestDto){
        if(!requestDto.getFiles().isEmpty()){
            boardService.saveArticleWithFiles(requestDto);
            return ResponseEntity.accepted().body(new ApiResponse(true, "글이 성공적으로 등록되었습니다."));
        }
        boardService.saveArticle(requestDto);
        return ResponseEntity.accepted().body(new ApiResponse(true, "글이 정상적으로 등록되었습니다."));
    }

    @PutMapping("/{id}/{type}")
    public ResponseEntity<?> updateBoardType(@PathVariable Long id, @PathVariable BoardType type){
        return ResponseEntity.ok().body(boardService.updateArticleType(id, type));
    }

    @GetMapping(value = "")
    public PageMaker list(PageVO vo){
        Pageable page = vo.makePageable(0, "id");
        Page<Board> result = boardService.pagination(vo, page);

        return new PageMaker(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> viewBoardDetail(@PathVariable Long id){
        return ResponseEntity.ok().body(boardService.findArticleById(id));
    }
}

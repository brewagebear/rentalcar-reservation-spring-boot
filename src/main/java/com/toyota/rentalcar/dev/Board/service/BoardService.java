package com.toyota.rentalcar.dev.Board.service;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardType;
import com.toyota.rentalcar.dev.Board.model.File;
import com.toyota.rentalcar.dev.Board.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.Board.dto.FileSaveRequestDto;
import com.toyota.rentalcar.dev.commons.exceptions.BadRequestException;
import com.toyota.rentalcar.dev.Board.repositories.BoardRepository;
import com.toyota.rentalcar.dev.Board.repositories.FileRepository;
import com.toyota.rentalcar.dev.Board.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository  fileRepository;

    /***
     * 파일이 없는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @return boardId
     */
    @Transactional
    public Long saveArticle(@Valid BoardSaveRequestDto requestDto){
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    /***
     * 파일이 있는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @return
     */
    @Transactional
    public void saveArticleWithFiles(@Valid BoardSaveRequestDto requestDto) {
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
    public Board findArticleById(Long id) {
        Optional<Board> maybeBoard = boardRepository.findById(id);
        if(maybeBoard.isPresent()) {
            maybeBoard.get().updateViewHit(1);
            return maybeBoard.get();
        }
        throw new BadRequestException(id + " 해당 게시글을 찾을 수가 없습니다.");
    }

    @Transactional
    public Board updateArticleType(Long id, BoardType type){
        Optional<Board> maybeBoard = boardRepository.findById(id);
        if(maybeBoard.isPresent()) {
            maybeBoard.get().updateBoardType(type);
            return maybeBoard.get();
        }
        throw new BadRequestException(id + " 해당 게시글을 찾을 수가 없습니다.");
    }
}

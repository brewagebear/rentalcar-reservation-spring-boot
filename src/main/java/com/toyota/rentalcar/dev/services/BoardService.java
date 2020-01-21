package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.Board;
import com.toyota.rentalcar.dev.domain.FileEntity;
import com.toyota.rentalcar.dev.dto.BoardResponseDto;
import com.toyota.rentalcar.dev.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.dto.FileSaveRequestDto;
import com.toyota.rentalcar.dev.dto.UploadFileResponse;
import com.toyota.rentalcar.dev.repositories.BoardRepository;
import com.toyota.rentalcar.dev.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository  fileRepository;

    @Transactional
    public Page<Board> findJsonDataByCondition(String orderBy, String direction, int page, int size){
        Sort sort = null;

        if(direction.equals("ASC")){
            sort = Sort.by(Direction.ASC, orderBy);
        }

        if(direction.equals("DESC")){
            sort = Sort.by(Direction.DESC, orderBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Board> data = boardRepository.findAll(pageable);
        return data;
    }

    /***
     * 파일이 없는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @return boardId
     */
    @Transactional
    public Long saveArticle(@Valid BoardSaveRequestDto requestDto, HttpSession session){
        session.invalidate();
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    /***
     * 파일이 있는 게시글 등록 Method
     * 현재 게시판의 id값을 리턴하는데 Request 리턴하게끔 컨트롤러에서 바꿔야함.
     * @param requestDto
     * @param session
     * @return
     */
    @Transactional
    public Long saveArticleWithFiles(@Valid BoardSaveRequestDto requestDto, HttpSession session) {
        Board board = requestDto.toEntity();

        List<UploadFileResponse> files = (List<UploadFileResponse>) session.getAttribute("files");
        List<FileEntity> entities = new ArrayList<>();

        for (UploadFileResponse file : files) {
                FileSaveRequestDto dto = new FileSaveRequestDto();
                dto.setFileName(file.getFileName());
                dto.setUri(file.getUri());
                dto.setBoard(board);
                entities.add(dto.toEntity());
            }
        session.invalidate();
        fileRepository.saveAll(entities);
        return boardRepository.save(board).getId();
    }

    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}

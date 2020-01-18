package com.toyota.rentalcar.dev.services;

import com.toyota.rentalcar.dev.domain.Board;
import com.toyota.rentalcar.dev.domain.FileEntity;
import com.toyota.rentalcar.dev.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.dto.FileSaveRequestDto;
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

import javax.validation.Valid;
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

    @Transactional
    public Long saveArticle(@Valid BoardSaveRequestDto requestDto) {
        Board target = requestDto.toEntity();
        if (requestDto.getFiles().isEmpty()){
            boardRepository.save(target);
            return target.getId();

        } else {
            List<FileEntity> files = requestDto.getFiles();

            for (FileEntity file : files) {
                FileSaveRequestDto fileRequestDto = new FileSaveRequestDto();
                fileRequestDto.setBoard(target);
                fileRequestDto.setFileName(file.getFileName());
                fileRepository.save(fileRequestDto.toEntity());
            }
            boardRepository.save(target);
        }
        return target.getId();
    }

    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}

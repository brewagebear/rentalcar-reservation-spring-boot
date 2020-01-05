package com.toyota.rentalcar.dev.controllers;


import com.toyota.rentalcar.dev.domain.Board;
import com.toyota.rentalcar.dev.domain.Direction;
import com.toyota.rentalcar.dev.domain.OrderBy;
import com.toyota.rentalcar.dev.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.dto.RentalCarSaveRequestDto;
import com.toyota.rentalcar.dev.dto.UploadFileResponse;
import com.toyota.rentalcar.dev.exceptions.PaginationSortingException;
import com.toyota.rentalcar.dev.repositories.BoardRepository;
import com.toyota.rentalcar.dev.services.BoardService;
import com.toyota.rentalcar.dev.services.FileService;
import com.toyota.rentalcar.dev.vo.PageMaker;
import com.toyota.rentalcar.dev.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final FileService fileService;

    private final BoardRepository boardRepository;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

//    @GetMapping("list")
//    public void list(@ModelAttribute("PageVO") PageVO vo, Model model){
//        Pageable page = vo.makePageable(0, "id");
//
//        Page<Board> result = BoardService.select(
//
//        )
//
//    }

    @GetMapping("/list")
    public void list(@ModelAttribute("PageVO") PageVO vo, Model model){

        Pageable page = vo.makePageable(0, "id");

        Page<Board> result = boardRepository.findAll(
                boardRepository.makePredicate(vo.getType(), vo.getKeyword()), page);

        model.addAttribute("result", new PageMaker(result));
        model.addAttribute("pageVO", vo);
    }

    @GetMapping("/view")
    public List<Board> view(){
        return boardService.findAll();
    }

    @PostMapping("/upload")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file){
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multipart/{fileName:.+}")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){

        Resource resource = fileService.loadFileAsResource(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex){
            logger.info("파일 타입을 확인할 수가 없습니다.");
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/write")
    public void writeGET(@ModelAttribute("vo") Board vo){

    }

    @PostMapping("/write")
    public Long save(@RequestBody BoardSaveRequestDto requestDto){
        return boardService.save(requestDto);
    }

    @GetMapping(value = "/pagination",
                params = { "orderBy", "direction", "page", "size"})
    @ResponseBody
    public Page<Board> findJsonDataByPageAndSize(
            @RequestParam("orderBy")   String orderBy,
            @RequestParam("direction") String direction,
            @RequestParam("page")      int    page,
            @RequestParam("size")      int    size){

        if(!(direction.equals(Direction.ASCENDING.getDirectionCode())
                || direction.equals(Direction.DESCENDING.getDirectionCode()))){
            throw new PaginationSortingException("Invalid sort direction");
        }

        if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.USERID.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        return boardService.findJsonDataByCondition(orderBy, direction,  page, size);
    }
}

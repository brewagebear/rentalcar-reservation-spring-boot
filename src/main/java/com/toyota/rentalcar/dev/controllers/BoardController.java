package com.toyota.rentalcar.dev.controllers;


import com.toyota.rentalcar.dev.domain.Board;
import com.toyota.rentalcar.dev.domain.Direction;
import com.toyota.rentalcar.dev.domain.OrderBy;
import com.toyota.rentalcar.dev.dto.BoardSaveRequestDto;
import com.toyota.rentalcar.dev.exceptions.PaginationSortingException;
import com.toyota.rentalcar.dev.repositories.BoardRepository;
import com.toyota.rentalcar.dev.services.BoardService;
import com.toyota.rentalcar.dev.services.FileService;
import com.toyota.rentalcar.dev.vo.PageMaker;
import com.toyota.rentalcar.dev.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController extends SessionController {

    private final BoardService boardService;
    private final FileService fileService;
    private final BoardRepository boardRepository;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

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

    @PostMapping("/write")
    public Long save(@RequestBody BoardSaveRequestDto requestDto, HttpSession session){
        logger.info("Current Session Id : " + session.getId());

        Enumeration<String> attrNames = session.getAttributeNames();

        while(attrNames.hasMoreElements()){
            String attrName = attrNames.nextElement();
            Object attrValue = session.getAttribute(attrName);
            logger.info(attrName + " : " + attrValue);
        }

        if (session.getAttribute("files") == null || session.getAttribute("files").equals("")) {
            return boardService.saveArticle(requestDto, session);
        } else {
            logger.info(session.getAttribute("files").toString());
            return boardService.saveArticleWithFiles(requestDto, session);
        }
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

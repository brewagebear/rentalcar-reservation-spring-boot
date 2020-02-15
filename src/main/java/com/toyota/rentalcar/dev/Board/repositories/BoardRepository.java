package com.toyota.rentalcar.dev.Board.repositories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.BoardCategory;
import com.toyota.rentalcar.dev.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    public default Predicate makePredicate(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QBoard board = QBoard.board;

        if(type == null){
            return builder;
        }

        switch (type){
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;
            case "c":
                builder.and(board.content.like("%" + keyword + "%"));
                break;
            case "w":
                builder.and(board.userName.like("%" + keyword + "%"));
                break;
            case "tc":
                builder.and(board.title.like("%" + keyword + "%"))
                        .and(board.content.like("%" + keyword + "%"));
                break;
        }
        return builder;
    }

    public Optional<Board> findFirstByIdIsGreaterThanOrderByIdAsc(Long id);
    public Optional<Board> findFirstByIdIsLessThanOrderByIdDesc(Long id);

    @Query("select b from Board b where b.boardCategory = 'NOTICE'")
    public Page<Board> findByBoardCategoryNotice(Predicate predicate, Pageable pageable);

    @Query("select b from Board b where b.boardCategory = 'QNA'")
    public Page<Board> findByBoardCategoryQna(Predicate predicate, Pageable pageable);
}

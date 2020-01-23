package com.toyota.rentalcar.dev.Board.repositories;

import com.querydsl.core.BooleanBuilder;
import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.domain.QBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;

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

}

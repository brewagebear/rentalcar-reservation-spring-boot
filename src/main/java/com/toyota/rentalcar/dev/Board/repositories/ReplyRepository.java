package com.toyota.rentalcar.dev.Board.repositories;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    public List<Reply> findAllByBoard_IdOrderByIdAsc(Long board_id);
}

package com.toyota.rentalcar.dev.Board.repositories;

import com.toyota.rentalcar.dev.Board.model.Board;
import com.toyota.rentalcar.dev.Board.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findAllByBoard_IdOrderByIdAsc(Long boardId);
    public Optional<Reply> findByBoard_IdAndId(Long boardId, Long replyId);
}

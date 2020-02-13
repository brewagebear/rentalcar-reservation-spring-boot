package com.toyota.rentalcar.dev.Board.repositories;

import com.toyota.rentalcar.dev.Board.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<List<File>> findAllByBoard_Id(Long id);
}

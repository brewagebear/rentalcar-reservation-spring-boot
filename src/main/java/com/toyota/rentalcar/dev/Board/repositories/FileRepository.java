package com.toyota.rentalcar.dev.Board.repositories;

import com.toyota.rentalcar.dev.Board.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

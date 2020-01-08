package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}

package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.BorrowedDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedDateRepository extends JpaRepository<BorrowedDate, Long> {
    BorrowedDate findByCarId(Long id);
    List<BorrowedDate> findAll();

}

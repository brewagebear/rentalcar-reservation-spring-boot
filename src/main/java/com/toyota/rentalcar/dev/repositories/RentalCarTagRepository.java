package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.RentalCarTag;
import com.toyota.rentalcar.dev.domain.RentalCarTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalCarTagRepository extends JpaRepository<RentalCarTag, RentalCarTagId> {
}

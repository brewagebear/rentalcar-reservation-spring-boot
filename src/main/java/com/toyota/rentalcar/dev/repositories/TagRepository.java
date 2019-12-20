package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository <Tag, Long> {
}

package com.toyota.rentalcar.dev.repositories;

import com.toyota.rentalcar.dev.domain.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
}

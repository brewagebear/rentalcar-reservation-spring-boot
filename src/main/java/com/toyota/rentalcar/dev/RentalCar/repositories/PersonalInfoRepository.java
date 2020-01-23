package com.toyota.rentalcar.dev.RentalCar.repositories;

import com.toyota.rentalcar.dev.RentalCar.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
}

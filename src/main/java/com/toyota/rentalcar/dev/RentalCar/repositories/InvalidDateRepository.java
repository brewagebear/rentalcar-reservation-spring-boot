package com.toyota.rentalcar.dev.RentalCar.repositories;

import com.toyota.rentalcar.dev.RentalCar.model.InvalidDate;
import com.toyota.rentalcar.dev.RentalCar.model.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface InvalidDateRepository extends JpaRepository<InvalidDate, Long> {

    @Query("select car from RentalCar car " +
            "where car.id NOT IN (select date.car.id " +
            "from InvalidDate date " +
            "where date.startDate between :startDate and :endDate " +
            "or date.endDate between :startDate and :endDate) ")
    List<RentalCar> checkAvailableCars(@Param("startDate") Calendar startDate,
                                       @Param("endDate") Calendar endDate);
}

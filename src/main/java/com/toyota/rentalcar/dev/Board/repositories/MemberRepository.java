package com.toyota.rentalcar.dev.Board.repositories;

import com.toyota.rentalcar.dev.Board.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String userName);
}

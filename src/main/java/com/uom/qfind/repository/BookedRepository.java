package com.uom.qfind.repository;

import com.uom.qfind.model.Booked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookedRepository extends JpaRepository<Booked, String> {
    Optional<Booked> findFirstByVehicleIdAndUserId(String bookId, String memberId);
}

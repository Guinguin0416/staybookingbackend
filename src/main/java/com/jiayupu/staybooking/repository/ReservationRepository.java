package com.jiayupu.staybooking.repository;

import com.jiayupu.staybooking.model.Reservation;
import com.jiayupu.staybooking.model.Stay;
import com.jiayupu.staybooking.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuest(User guest);

    List<Reservation> findByStay(Stay stay);

    Reservation findByIdAndGuest(Long id, User guest); // for deletion

    List<Reservation> findByStayAndCheckoutDateAfter(Stay stay, LocalDate date);


}


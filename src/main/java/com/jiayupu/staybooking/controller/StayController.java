package com.jiayupu.staybooking.controller;

import com.jiayupu.staybooking.model.Reservation;
import com.jiayupu.staybooking.model.Stay;
import com.jiayupu.staybooking.model.User;
import com.jiayupu.staybooking.service.ReservationService;
import com.jiayupu.staybooking.service.StayService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.security.Principal;

@RestController
public class StayController {
    private final StayService stayService;
    private ReservationService reservationService;


    public StayController(StayService stayService, ReservationService reservationService) {
        this.stayService = stayService;
        this.reservationService = reservationService;

    }


    @GetMapping(value = "/stays")
    public List<Stay> listStays(Principal principal) {
        return stayService.listByUser(principal.getName());
    }

    @GetMapping(value = "/stays/{stayId}")
    public Stay getStay(@PathVariable Long stayId, Principal principal) {
        return stayService.findByIdAndHost(stayId, principal.getName());
    }

    @GetMapping(value = "/stays/reservations/{stayId}")
    public List<Reservation> listReservations(@PathVariable Long stayId) {
        return reservationService.listByStay(stayId);
    }

    @PostMapping("/stays")
    public void addStay(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("guest_number") int guestNumber,
            @RequestParam("images") MultipartFile[] images,
            Principal principal) {

        Stay stay = new Stay.Builder()
                .setName(name)
                .setAddress(address)
                .setDescription(description)
                .setGuestNumber(guestNumber)
                .setHost(new User.Builder().setUsername(principal.getName()).build())
                .build();
        stayService.add(stay, images);
    }

    @DeleteMapping("/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId, Principal principal) {
        stayService.delete(stayId, principal.getName());
    }



}



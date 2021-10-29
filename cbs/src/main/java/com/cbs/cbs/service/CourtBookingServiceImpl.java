package com.cbs.cbs.service;

import com.cbs.cbs.entity.Booking;
import com.cbs.cbs.entity.Court;
import com.cbs.cbs.entity.Player;
import com.cbs.cbs.repository.BookingRepository;
import com.cbs.cbs.repository.CourtRepository;
import com.cbs.cbs.repository.PlayerRepository;
import com.cbs.cbs.repository.generic.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

@Service
public class CourtBookingServiceImpl implements CourtBookingService{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtAllocationEngine courtAllocationEngine;

    @Override
    public Long registerBooking(String playerName, Date bookingDate) {
        Player playerEntity = null;
        var player = playerRepository.findAll().stream().filter(p ->
                p!=null && p.getName().equals(playerName)
        ).findFirst();

        if(!player.isPresent()) {
            playerEntity = playerRepository.save(new Player(0L, playerName));
        }
        else {
            playerEntity = player.get();
        }

        var booking = new Booking(0L, bookingDate,playerEntity);
        var savedBooking = bookingRepository.save(booking);
        ExecutorService pool = Executors.newFixedThreadPool(50);
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> courtAllocationEngine.allocate(savedBooking));
        CompletableFuture.allOf(cf1);
        return savedBooking.getId();
    }

}

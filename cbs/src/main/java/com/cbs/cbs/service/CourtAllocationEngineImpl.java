package com.cbs.cbs.service;

import com.cbs.cbs.entity.Booking;
import com.cbs.cbs.repository.BookingRepository;
import com.cbs.cbs.repository.CourtRepository;
import com.cbs.cbs.repository.generic.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CourtAllocationEngineImpl implements CourtAllocationEngine{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private PlayerNotificationService playerNotificationService;

    @Override
    public void allocate(Booking currentBooking) {
        var courts = courtRepository.findAll();
        //Create a map of court and empty bookings
        Map<Long,List<Booking>> courtBookingMap = courts.stream().collect(Collectors.toMap(k -> k.getCourtId(), v -> new ArrayList<Booking>()));

        //get all bookings for  date
        var bookings = bookingRepository.findAll().stream().
                                    filter(x -> x.getBookingDate().compareTo(currentBooking.getBookingDate()) == 0).collect(Collectors.toList());
        //update existing bookings
        bookings.stream().forEach(booking -> {
            if(booking.getCourtId() != null) {
                addBooking(courtBookingMap, booking);
            }
        });

        //get available court for the required date
        var availableCourt = courtBookingMap.entrySet().stream().filter(x ->
            x.getValue().size() < 4
        ).findFirst();

        if(availableCourt.isEmpty()) {
            currentBooking.setBookingUnavailable(false);
            playerNotificationService.notify(currentBooking);
            return;
        }

        //append existing courtless bookings to the available court
        courtBookingMap.put(availableCourt.get().getKey(), bookings.stream().filter(x -> x.getCourtId() == null).collect(Collectors.toList()));

        //allocate court once it reaches size of 4 and notify players
        var bookingsToSave = courtBookingMap.entrySet().stream().filter(entry ->
                entry.getValue().size() ==4 && entry.getValue().stream().allMatch(x -> x.getCourtId() == null)
                ).map(x -> Map.entry(x.getKey(), x.getValue())).collect(Collectors.toList());


        bookingsToSave.stream().forEach (y -> {
                    y.getValue().forEach(b-> {
                        b.setCourtId(y.getKey());
                        var future = CompletableFuture.supplyAsync(() -> bookingRepository.save(b)).thenAccept(booking -> {
                            playerNotificationService.notify(booking);
                        });
                        CompletableFuture.allOf(future);
                    });
                });
    }

    private void addBooking(Map<Long, List<Booking>> courtBookingMap, Booking booking) {
        var existingBookings =  courtBookingMap.get(booking.getCourtId());
        existingBookings.add(booking);
        courtBookingMap.put(booking.getCourtId(), existingBookings);
    }
}

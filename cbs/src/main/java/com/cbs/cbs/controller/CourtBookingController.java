package com.cbs.cbs.controller;

import com.cbs.cbs.entity.Booking;
import com.cbs.cbs.entity.Court;
import com.cbs.cbs.model.Response;
import com.cbs.cbs.repository.BookingRepository;
import com.cbs.cbs.repository.CourtRepository;
import com.cbs.cbs.repository.PlayerRepository;
import com.cbs.cbs.service.CourtBookingService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/v1")
@Api(value = "Supports GET, POST, PATCH, PUT, DELETE operations", tags = "cbsApi", hidden = true)
public class CourtBookingController{
    @Autowired
    private CourtRepository courtRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CourtBookingService courtBookingService;


    Logger log = LoggerFactory.getLogger(CourtBookingController.class);
    @ApiOperation(value = "Lists all the courts", notes = "")
    @GetMapping(value = "courts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<Court>, String>> getCourts()
    {
        var courts = courtRepository.findAll();
        return ResponseEntity.ok(new Response(courts, ""));
    }

    @ApiOperation(value = "Lists all the bookings", notes = "")
    @GetMapping(value = "bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<List<Booking>, String>> getBookings()
    {
        var bookings = bookingRepository.findAll();
        return ResponseEntity.ok(new Response(bookings, ""));
    }

    @ApiOperation(value = "Register for a court for a date", notes = "")
    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Long, String>> register(
            @ApiParam(name = "date", required = true, example = "2021-02-10") @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(required = true) String date,
            @ApiParam(name = "name", required = true)
            @RequestParam(required = true) String name
    )
    {
        Long bookingId = null;
        var df = new SimpleDateFormat("yyyy-MM-dd");
        var dataMap = new HashMap<String, Long>();
        try {
           Date bookingDate = df.parse(date);
           bookingId =  courtBookingService.registerBooking(name, bookingDate);
           dataMap.put("bookingId", bookingId);
        }
        catch(Exception ex) {
            log.error(ex.getMessage());
            return new ResponseEntity(new Response(null, "Unable to create booking."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new Response(dataMap,""));
    }
}

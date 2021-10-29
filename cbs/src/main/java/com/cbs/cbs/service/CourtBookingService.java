package com.cbs.cbs.service;

import com.cbs.cbs.entity.Player;

import java.time.LocalDate;
import java.util.Date;

public interface CourtBookingService {
    Long registerBooking(String player, Date bookingDate);
}

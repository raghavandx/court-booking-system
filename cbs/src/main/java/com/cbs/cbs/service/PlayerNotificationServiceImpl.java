package com.cbs.cbs.service;

import com.cbs.cbs.controller.CourtBookingController;
import com.cbs.cbs.entity.Booking;
import com.cbs.cbs.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerNotificationServiceImpl implements  PlayerNotificationService {

    Logger log = LoggerFactory.getLogger(PlayerNotificationServiceImpl.class);
    @Override
    public void notify(Booking booking) {
        if(booking.isBookingUnavailable()) {
            log.info(String.format("No more bookings available for %s.  Please choose another date", booking.getBookingDate()));
        }else {
            log.info(String.format("Court id : %s confirmed for %s on %s", booking.getCourtId(), booking.getPlayer().getName(), booking.getBookingDate()));
        }
    }
}
